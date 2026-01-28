import re
import cv2
import numpy as np
from easyocr import Reader
from attendance_system.utils.mongodb import MongoDB
from attendance_system.utils.logger import logger


class StudentCardService:
    _reader = Reader(['vi'], gpu=False)

    @staticmethod
    def recognize_student_id(image_bytes: bytes):
        img_array = np.frombuffer(image_bytes, np.uint8)
        img = cv2.imdecode(img_array, cv2.IMREAD_COLOR)

        if img is None:
            raise ValueError("Invalid image")

        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        results = StudentCardService._reader.readtext(
            gray,
            detail=0,
            paragraph=True
        )

        logger.info(results)

        full_text = " ".join(results).upper()

        full_text = (
            full_text
            .replace("O", "0")
            .replace("I", "1")
            .replace("L", "1")
        )

        compact = re.sub(r"\s+", "", full_text)
        match = re.search(r"\d{3}[A4]\d{6}", compact)

        if not match:
            return None

        student_id = match.group()
        student_id = (
                student_id[:3]
                + "A"
                + student_id[4:]
        )

        return student_id


    @staticmethod
    def verify_student_in_class(student_id: str, class_id: str):
        return MongoDB.get_collection("students").find_one(
            {
                "student_id": student_id,
                "class_id": class_id
            },
            {"_id": 0}
        )

