from sklearn.metrics.pairwise import cosine_similarity
from attendance_system.service.face_embedding_service import FaceEmbeddingService
from attendance_system.utils.mongodb import MongoDB
import numpy as np
import logging

logger = logging.getLogger(__name__)


class VerifyFaceService:
    THRESHOLD = 0.8

    @classmethod
    def verify(cls, image_bytes: bytes) -> dict:
        try:
            collection = MongoDB.get_collection("student_face_embeddings")
            collection.create_index("student_id_hash", unique=True)

            records = list(collection.find({}, {
                "student_id_hash": 1,
                "embeddings": 1,
                "model_version": 1
            }))

            if not records:
                return {
                    "found": False,
                    "message": "No face embeddings in database"
                }

            try:
                new_emb = FaceEmbeddingService.image_to_embedding(image_bytes)
            except ValueError as e:
                return {
                    "found": False,
                    "message": str(e)
                }

            best_score = -1
            best_match = None
            all_scores = []

            for record in records:
                record_embeddings = record["embeddings"]
                if not record_embeddings:
                    continue

                similarities = cosine_similarity([new_emb], record_embeddings)[0]
                max_similarity = np.max(similarities)
                all_scores.append(max_similarity)

                if max_similarity > best_score:
                    best_score = max_similarity
                    best_match = record

            if best_score < cls.THRESHOLD:
                return {
                    "found": False,
                    "confidence": round(best_score, 3),
                    "all_scores": [round(s, 3) for s in sorted(all_scores, reverse=True)[:5]]
                }

            return {
                "found": True,
                "student_id_hash": best_match["student_id_hash"],
                "confidence": round(best_score, 3),
                "model_version": best_match.get("model_version", "unknown")
            }

        except Exception as e:
            logger.error(f"Error in face verification: {str(e)}", exc_info=True)
            return {
                "found": False,
                "message": f"Verification failed: {str(e)}"
            }