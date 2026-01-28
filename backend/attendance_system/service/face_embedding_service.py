import cv2
import numpy as np
from mtcnn import MTCNN
from keras_facenet import FaceNet


class FaceEmbeddingService:
    detector = MTCNN()
    embedder = FaceNet()

    @staticmethod
    def image_to_embedding(image_bytes: bytes) -> list:
        np_img = np.frombuffer(image_bytes, np.uint8)
        img = cv2.imdecode(np_img, cv2.IMREAD_COLOR)

        rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        faces = FaceEmbeddingService.detector.detect_faces(rgb)

        if not faces:
            raise ValueError("No face detected")

        x, y, w, h = faces[0]["box"]
        face = rgb[y:y+h, x:x+w]

        face = cv2.resize(face, (160, 160))
        face = face.astype("float32")
        mean, std = face.mean(), face.std()
        face = (face - mean) / std
        face = np.expand_dims(face, axis=0)

        emb = FaceEmbeddingService.embedder.embeddings(face)[0]
        return emb.tolist()

    @staticmethod
    def images_to_embeddings(images: list) -> list:
        embeddings = []
        for img in images:
            embeddings.append(
                FaceEmbeddingService.image_to_embedding(img)
            )
        return embeddings
