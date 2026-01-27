import os
import cv2
import numpy as np
from mtcnn import MTCNN
from keras_facenet import FaceNet
from sklearn.preprocessing import Normalizer

DATASET_PATH = "dataset"   # đổi nếu khác
detector = MTCNN()
embedder = FaceNet()
normalizer = Normalizer(norm='l2')

X = []
y = []

for student_id in os.listdir(DATASET_PATH):
    student_dir = os.path.join(DATASET_PATH, student_id)

    if not os.path.isdir(student_dir):
        continue

    for img_name in os.listdir(student_dir):
        img_path = os.path.join(student_dir, img_name)

        img = cv2.imread(img_path)
        if img is None:
            print(f"Không đọc được ảnh: {img_path}")
            continue

        rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        faces = detector.detect_faces(rgb)

        if len(faces) == 0:
            print(f"Không thấy mặt: {img_path}")
            continue

        x, y0, w, h = faces[0]["box"]
        h_img, w_img, _ = rgb.shape

        x = max(0, x)
        y0 = max(0, y0)
        x2 = min(w_img, x + w)
        y2 = min(h_img, y0 + h)

        face = rgb[y0:y2, x:x2]
        if face.size == 0:
            continue

        face = cv2.resize(face, (160, 160))
        face = face.astype("float32")
        mean, std = face.mean(), face.std()
        face = (face - mean) / std
        face = np.expand_dims(face, axis=0)

        embedding = embedder.embeddings(face)[0]

        X.append(embedding)
        y.append(student_id)

        print(f"OK: {student_id} - {img_name}")

# ================== CHECK TRƯỚC KHI NORMALIZE ==================
X = np.asarray(X)
y = np.asarray(y)

if X.shape[0] == 0:
    raise RuntimeError("KHÔNG CÓ EMBEDDING NÀO ĐƯỢC TẠO. CHECK DATASET!")

X = normalizer.fit_transform(X)

np.save("embeddings.npy", X)
np.save("labels.npy", y)

print("done")
print("➡ embeddings.npy:", X.shape)
print("➡ labels.npy:", y.shape)
