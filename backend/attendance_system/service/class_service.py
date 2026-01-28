
from attendance_system.domain.dto.class_dto import ClassDTO
from attendance_system.domain.entity.class_entity import ClassEntity
from attendance_system.utils.mongodb import MongoDB


class ClassService:

    @staticmethod
    def insert_class(dto: ClassDTO) -> dict:
        collection = MongoDB.get_collection("classes")

        entity = ClassEntity(dto)
        collection.insert_one(entity.to_dict())

        return {
            "class_id": dto.class_id,
            "class_name": dto.class_name,
            "room": dto.room,
            "date": dto.date,
        }
