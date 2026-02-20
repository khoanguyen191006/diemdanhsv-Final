import cv2
import numpy as np
from mtcnn import MTCNN
from keras_facenet import FaceNet
import threading
import logging

logger = logging.getLogger(__name__)


class FaceEmbeddingService:
    _detector = None
    _embedder = None
    _lock = threading.Lock()

    @classmethod
    def _get_detector(cls):
        if cls._detector is None:
            with cls._lock:
                if cls._detector is None:
                    cls._detector = MTCNN()
        return cls._detector

    @classmethod
    def _get_embedder(cls):
        if cls._embedder is None:
            with cls._lock:
                if cls._embedder is None:
                    cls._embedder = FaceNet()
        return cls._embedder

    @classmethod
    def image_to_embedding(cls, image_bytes: bytes) -> list:
        try:
            np_img = np.frombuffer(image_bytes, np.uint8)
            img = cv2.imdecode(np_img, cv2.IMREAD_COLOR)

            if img is None:
                raise ValueError("Invalid image format")

            rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

            detector = cls._get_detector()
            faces = detector.detect_faces(rgb)

            if not faces:
                raise ValueError("No face detected in image")

            x, y, w, h = faces[0]["box"]
            x, y = max(0, x), max(0, y)
            face = rgb[y:y + h, x:x + w]

            if face.size == 0:
                raise ValueError("Invalid face region detected")

            face = cv2.resize(face, (160, 160))
            face = face.astype("float32")

            face = (face - 127.5) / 127.5
            face = np.expand_dims(face, axis=0)

            embedder = cls._get_embedder()
            emb = embedder.embeddings(face)[0]

            return emb.tolist()

        except Exception as e:
            logger.error(f"Error generating embedding: {str(e)}")
            raise ValueError(f"Failed to generate face embedding: {str(e)}")

    @classmethod
    def images_to_embeddings(cls, images: list) -> list:
        embeddings = []
        errors = []

        for i, img_bytes in enumerate(images):
            try:
                emb = cls.image_to_embedding(img_bytes)
                embeddings.append(emb)
            except ValueError as e:
                errors.append(f"Image {i+1}: {str(e)}")

        if not embeddings:
            error_msg = "; ".join(errors) if errors else "No valid faces in any image"
            raise ValueError(f"Failed to generate any embeddings: {error_msg}")

        if errors:
            logger.warning(f"Some images failed: {'; '.join(errors)}")

        return embeddings