from dataclasses import dataclass
from typing import Optional


@dataclass
class StudentDTO:
    student_id: str
    student_name: str
    class_id: str
    email: Optional[str] = None
