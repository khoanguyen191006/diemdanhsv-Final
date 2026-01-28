from pymongo import MongoClient


class MongoDB:
    _client = None

    @classmethod
    def get_collection(cls, name: str):
        if cls._client is None:
            cls._client = MongoClient("mongodb://localhost:27017/")
        db = cls._client["attendance_system"]
        return db[name]
