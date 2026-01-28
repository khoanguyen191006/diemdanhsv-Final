from bson import ObjectId
from datetime import datetime


class ClassEntity:
    def __init__(self, dto):
        self._id = ObjectId()
        self.class_id = dto.class_id
        self.class_name = dto.class_name

        self.date_time = {
            "date": datetime.now().strftime("%d-%m-%Y"),
            "time": datetime.now().strftime("%H:%M:%S"),
        }

        self.lecturer_information = {
            "lecturer_id": dto.lecturer_id,
            "lecturer_name": dto.lecturer_name,
        }

        self.ListOfStudents = {
            "tongsv": 0,
            "ds": [],
        }

        self.class_information = {
            "date_time": {
                "date": dto.date,
                "time": self._resolve_shift(dto.shift),
            },
            "Room": dto.room,
        }

    def _resolve_shift(self, shift: int):
        if shift == 1:
            return {"start": "07:15", "end": "11:00"}
        if shift == 2:
            return {"start": "12:30", "end": "16:00"}
        if shift == 3:
            return {"start": "17:00", "end": "20:30"}
        raise ValueError("Invalid shift")

    def to_dict(self):
        return {
            "_id": self._id,
            "class_id": self.class_id,
            "class_name": self.class_name,
            "date_time": self.date_time,
            "lecturer_information": self.lecturer_information,
            "ListOfStudents": self.ListOfStudents,
            "class_information": self.class_information,
        }
