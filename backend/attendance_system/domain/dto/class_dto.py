from dataclasses import dataclass


@dataclass
class ClassDTO:
    class_id: str
    class_name: str
    room: str
    date: str
    shift: int
    lecturer_id: str
    lecturer_name: str
