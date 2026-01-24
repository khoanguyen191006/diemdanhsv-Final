import os
import cv2
import torch
import pickle
import numpy as np

from facenet_pytorch import MTCNN, InceptionResnetV1
from sklearn.preprocessing import LabelEncoder, Normalizer
from sklearn.svm import SVC

device = "cuda" if torch.cuda.is_available() else "cpu"

# Load model
mtcnn = MTCNN(image_size=160, margin=20, device=device)
facenet = InceptionResnetV1(pretrained="vggface2").eval().to(device)

# Load data cũ
embeddings = list(np.load("embeddings.npy"))
labels = list(np.load("labels.npy"))

def get_embedding(face_rgb):
    face_rgb = cv2.resize(face_rgb, (160, 160))
    face_rgb = face_rgb.astype(np.float32) / 255.0
    face_rgb = np.transpose(face_rgb, (2, 0, 1))
    face_rgb = torch.tensor(face_rgb).unsqueeze(0).to(device)

    with torch.no_grad():
        emb = facenet(face_rgb)

    return emb.cpu().numpy()[0]   # 512D

def detect_face(img_bgr):
    img_rgb = cv2.cvtColor(img_bgr, cv2.COLOR_BGR2RGB)
    boxes, _ = mtcnn.detect(img_rgb)

    if boxes is None:
        return None

    x1, y1, x2, y2 = boxes[0].astype(int)
    h, w, _ = img_rgb.shape
    x1, y1 = max(0, x1), max(0, y1)
    x2, y2 = min(w, x2), min(h, y2)

    return img_rgb[y1:y2, x1:x2]

def train_svm(embeddings, labels):
    X = np.array(embeddings)

    normalizer = Normalizer(norm="l2")
    X = normalizer.fit_transform(X)

    encoder = LabelEncoder()
    y = encoder.fit_transform(labels)

    model = SVC(kernel="linear", probability=True)
    model.fit(X, y)

    pickle.dump(model, open("svm_model.pkl", "wb"))
    pickle.dump(encoder, open("label_encoder.pkl", "wb"))
    pickle.dump(normalizer, open("normalizer.pkl", "wb"))

    np.save("embeddings.npy", embeddings)
    np.save("labels.npy", labels)

    print("✅ Update sinh viên mới thành công")

def add_new_student(mssv, images):
    if mssv in labels:
        print(f"⚠ MSSV {mssv} đã tồn tại")
        return

    added = 0

    for img in images:
        face = detect_face(img)
        if face is None:
            continue

        emb = get_embedding(face)
        embeddings.append(emb)
        labels.append(mssv)
        added += 1

    if added == 0:
        print("❌ Không thêm được ảnh hợp lệ")
        return

    train_svm(embeddings, labels)
    print(f"🎉 Đã thêm {added} ảnh cho MSSV {mssv}")
