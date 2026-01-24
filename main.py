import os
import re
import time
import cv2
import numpy as np
from datetime import datetime

from mtcnn import MTCNN
from keras_facenet import FaceNet
from easyocr import Reader
from pymongo import MongoClient
from sklearn.metrics.pairwise import cosine_similarity

# ================== FACENET ==================
detector = MTCNN()
embedder = FaceNet()

embeddings = np.load("embeddings.npy")
labels = np.load("labels.npy")   # labels = MSSV

THRESHOLD = 0.8   # Ngưỡng an toàn

def get_embedding(face):
    face = cv2.resize(face, (160, 160))
    face = face.astype("float32")
    mean, std = face.mean(), face.std()
    face = (face - mean) / std
    face = np.expand_dims(face, axis=0)
    return embedder.embeddings(face)[0]


def recognize_face(face_img):
    emb = get_embedding(face_img)
    sims = cosine_similarity([emb], embeddings)[0]
    idx = np.argmax(sims)

    if sims[idx] >= THRESHOLD:
        return labels[idx], sims[idx]
    return "Unknown", sims[idx]


# ================== OCR STUDENT CARD ==================
def recognize_student_card(reader, camera_index=0):
    cap = cv2.VideoCapture(camera_index)
    time.sleep(2)

    frame = None
    for _ in range(10):
        ret, frame = cap.read()

    cap.release()

    if frame is None:
        return None, None

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    texts = reader.readtext(gray)

    full_text = "\n".join([t[1] for t in texts])
    full_text = re.sub(r"[^\x00-\x7F\u00C0-\u1EF9\s]", "", full_text)
    full_text = full_text.replace("O", "0").replace("l", "1")

    mssv = re.search(r"\b\d{8}\b", full_text)

    if mssv:
        return {"student_id": mssv.group()}, frame

    return None, frame


# ================== FACE ID ==================
def faceid(collection, valid_students):
    cam = cv2.VideoCapture(1)
    checked_ids = set()

    while True:
        ret, frame = cam.read()
        if not ret:
            break

        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        faces = detector.detect_faces(rgb)

        h_img, w_img, _ = rgb.shape

        for face in faces:
            x, y, w, h = face["box"]

            x = max(0, x)
            y = max(0, y)
            x2 = min(w_img, x + w)
            y2 = min(h_img, y + h)

            face_img = rgb[y:y2, x:x2]
            if face_img.size == 0:
                continue

            name, conf = recognize_face(face_img)

            if name in valid_students and name not in checked_ids:
                now = datetime.now().strftime("%H:%M:%S")

                collection.update_one(
                    {"ListOfStudents.ds.student_id": name},
                    {"$set": {
                        "ListOfStudents.ds.$.RollCall_time.FaceID": 1,
                        "ListOfStudents.ds.$.RollCall_time.status.dadiemdanh": 1,
                        "ListOfStudents.ds.$.RollCall_time.time_in": now
                    }}
                )

                checked_ids.add(name)
                print(f"Điểm danh: {name} | conf={conf:.3f}")

            color = (0, 255, 0) if name != "Unknown" else (0, 0, 255)
            cv2.rectangle(frame, (x, y), (x2, y2), color, 2)
            cv2.putText(frame, f"{name} ({conf:.2f})",
                        (x, y - 10),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.7, color, 2)

        cv2.imshow("FACENET ATTENDANCE", frame)
        if cv2.waitKey(1) & 0xFF == ord("q"):
            break

    cam.release()
    cv2.destroyAllWindows()


# ================== MAIN ==================
def main():
    reader = Reader(['vi'], gpu=False)

    client = MongoClient("mongodb://localhost:27017/")
    db = client["class"]
    collection = db["class"]

    today = datetime.now().strftime("%d-%m-%Y")

    print("📸 Đưa thẻ sinh viên trước camera...")
    result, _ = recognize_student_card(reader)

    if not result:
        print("Không đọc được thẻ sinh viên")
        return

    mssv = result["student_id"]

    student = collection.find_one({
        "ListOfStudents.ds.student_id": mssv
    })

    if not student:
        print("Không tìm thấy sinh viên")
        return

    if student["class_information"]["date_time"]["date"] != today:
        print(" Hôm nay không có lớp")
        return

    collection.update_one(
        {"ListOfStudents.ds.student_id": mssv},
        {"$set": {
            "ListOfStudents.ds.$.RollCall_time.check-mssv": 1
        }}
    )

    print("✅ MSSV hợp lệ – chuyển sang FaceID")

    valid_students = [
        s["student_id"]
        for cls in collection.find(
            {"ListOfStudents.ds.RollCall_time.check-mssv": 1},
            {"ListOfStudents.ds.student_id": 1}
        )
        for s in cls["ListOfStudents"]["ds"]
        if s["RollCall_time"]["check-mssv"] == 1
    ]

    if not valid_students:
        print("Không có SV hợp lệ để FaceID")
        return

    faceid(collection, valid_students)


if __name__ == "__main__":
    main()
