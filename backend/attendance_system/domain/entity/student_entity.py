from bson import ObjectId
from datetime import datetime
from typing import List


class StudentEntity:
    def __init__(self, dto, embeddings: List[list] = None):
        self._id = ObjectId()
        self.student_id = dto.student_id
        self.student_name = dto.student_name
        self.class_id = dto.class_id
        self.email = dto.email
        self.embeddings = embeddings or []
        self.created_at = datetime.utcnow()

    def to_dict(self):
        return {
            "_id": self._id,
            "student_id": self.student_id,
            "student_name": self.student_name,
            "class_id": self.class_id,
            "email": self.email,
            "embeddings": self.embeddings,
            "created_at": self.created_at,
        }