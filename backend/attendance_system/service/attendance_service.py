from sklearn.metrics.pairwise import cosine_similarity
from attendance_system.service.face_embedding_service import FaceEmbeddingService
from attendance_system.utils.mongodb import MongoDB
from datetime import datetime


class AttendanceService:
    THRESHOLD = 0.8

    @staticmethod
    def face_attendance(class_id: str, image_bytes: bytes):
        students = list(
            MongoDB.get_collection("students")
            .find({"class_id": class_id})
        )

        if not students:
            raise ValueError("No students in class")

        new_emb = FaceEmbeddingService.image_to_embedding(image_bytes)

        best_score = -1
        best_student = None

        for student in students:
            for emb in student["embeddings"]:
                score = cosine_similarity(
                    [new_emb], [emb]
                )[0][0]

                if score > best_score:
                    best_score = score
                    best_student = student

        if best_score < AttendanceService.THRESHOLD:
            return {"status": "unknown"}

        now = datetime.now().strftime("%H:%M:%S")

        MongoDB.get_collection("classes").update_one(
            {
                "class_id": class_id,
                "ListOfStudents.ds.student_id": best_student["student_id"]
            },
            {
                "$set": {
                    "ListOfStudents.ds.$.RollCall_time.FaceID": 1,
                    "ListOfStudents.ds.$.RollCall_time.status.dadiemdanh": 1,
                    "ListOfStudents.ds.$.RollCall_time.time_in": now
                }
            }
        )

        return {
            "student_id": best_student["student_id"],
            "student_name": best_student["student_name"],
            "email": best_student["email"],
            "confidence": round(best_score, 3)
        }
