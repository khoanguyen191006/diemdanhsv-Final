import os
import ssl
ssl._create_default_https_context = ssl._create_unverified_context

import cv2
import torch
import numpy as np
from facenet_pytorch import MTCNN, InceptionResnetV1

device = 'cuda' if torch.cuda.is_available() else 'cpu'

mtcnn = MTCNN(
    image_size=160,
    margin=20,
    keep_all=False,
    device=device
)

facenet = InceptionResnetV1(
    pretrained='vggface2'
).eval().to(device)


def get_embedding(face_rgb):
    face_rgb = cv2.resize(face_rgb, (160, 160))
    face_rgb = face_rgb.astype(np.float32) / 255.0
    face_rgb = np.transpose(face_rgb, (2, 0, 1))
    face_rgb = torch.tensor(face_rgb).unsqueeze(0).to(device)

    with torch.no_grad():
        emb = facenet(face_rgb)

    return emb.cpu().numpy()[0]


embeddings = []
labels = []

dataset_path = "dataset"

for person in os.listdir(dataset_path):
    person_dir = os.path.join(dataset_path, person)

    for img_name in os.listdir(person_dir):
        img_path = os.path.join(person_dir, img_name)
        img_bgr = cv2.imread(img_path)
        if img_bgr is None:
            continue

        img_rgb = cv2.cvtColor(img_bgr, cv2.COLOR_BGR2RGB)

        boxes, _ = mtcnn.detect(img_rgb)

        if boxes is not None:
            x1, y1, x2, y2 = boxes[0].astype(int)
            face = img_rgb[y1:y2, x1:x2]

            emb = get_embedding(face)
            embeddings.append(emb)
            labels.append(person)

np.save("embeddings.npy", np.array(embeddings))
np.save("labels.npy", np.array(labels))

print("Đã trích xuất embedding")
