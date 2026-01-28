from attendance_system.domain.dto.student_dto import StudentDTO
from attendance_system.domain.entity.student_entity import StudentEntity
from attendance_system.service.face_embedding_service import FaceEmbeddingService
from attendance_system.utils.mongodb import MongoDB


class StudentService:

    @staticmethod
    def insert_student(dto: StudentDTO, images: list = None) -> dict:
        collection = MongoDB.get_collection("students")

        embeddings = []
        if images:
            embeddings = FaceEmbeddingService.images_to_embeddings(images)

        entity = StudentEntity(dto, embeddings)
        collection.insert_one(entity.to_dict())

        class_col = MongoDB.get_collection("classes")
        class_col.update_one(
            {"class_id": dto.class_id},
            {
                "$push": {
                    "ListOfStudents.ds": {
                        "student_id": dto.student_id,
                        "student_name": dto.student_name,
                        "RollCall_time": {
                            "FaceID": 0,
                            "check-mssv": 0,
                            "status": {"dadiemdanh": 0}
                        }
                    }
                },
                "$inc": {"ListOfStudents.tongsv": 1}
            }
        )

        return {
            "student_id": dto.student_id,
            "student_name": dto.student_name,
            "class_id": dto.class_id,
            "total_embeddings": len(embeddings)
        }
