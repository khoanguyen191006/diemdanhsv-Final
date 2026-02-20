import re
import cv2
import numpy as np
import hashlib
from easyocr import Reader


class StudentCardService:
    _reader = Reader(['vi'], gpu=False)

    SALT = "STUDENT-ID-SALT-2026"

    @staticmethod
    def read_student_id(image_bytes: bytes) -> dict:
        student_id = StudentCardService._extract_student_id(image_bytes)

        if not student_id:
            return {
                "found": False,
                "message": "Không đọc được MSSV từ thẻ"
            }

        student_id_hash = StudentCardService._hash_student_id(student_id)

        return {
            "found": True,
            "student_id": student_id,
            "student_id_hash": student_id_hash
        }

    @staticmethod
    def _extract_student_id(image_bytes: bytes) -> str | None:
        img_array = np.frombuffer(image_bytes, np.uint8)
        img = cv2.imdecode(img_array, cv2.IMREAD_COLOR)

        if img is None:
            return None

        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

        results = StudentCardService._reader.readtext(
            gray, detail=0, paragraph=True
        )

        full_text = " ".join(results).upper()
        full_text = (
            full_text.replace("O", "0")
            .replace("I", "1")
            .replace("L", "1")
        )

        compact = re.sub(r"\s+", "", full_text)
        match = re.search(r"\d{3}[A4]\d{6}", compact)

        if not match:
            return None

        raw = match.group()
        return raw[:3] + "A" + raw[4:]

    @staticmethod
    def _hash_student_id(student_id: str) -> str:
        raw = f"{student_id}{StudentCardService.SALT}"
        return hashlib.sha256(raw.encode()).hexdigest()