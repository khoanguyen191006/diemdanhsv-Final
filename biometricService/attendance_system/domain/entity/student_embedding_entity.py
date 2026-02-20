from bson import ObjectId
from datetime import datetime
from typing import List, Optional


class StudentEmbeddingEntity:
    def __init__(
            self,
            student_id_hash: str,
            embeddings: List[list],
            model_version: str,
            updated_at: Optional[datetime] = None
    ):
        self._id = ObjectId()
        self.student_id_hash = student_id_hash
        self.embeddings = embeddings
        self.model_version = model_version
        self.created_at = datetime.utcnow()
        self.updated_at = updated_at or self.created_at

    def to_dict(self):
        return {
            "_id": self._id,
            "student_id_hash": self.student_id_hash,
            "embeddings": self.embeddings,
            "model_version": self.model_version,
            "created_at": self.created_at,
            "updated_at": self.updated_at
        }