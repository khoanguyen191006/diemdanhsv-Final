from pymongo import MongoClient


class MongoDB:
    username = "nguyenhuutrong11133_db_user"
    password = "HAU6mfQJYL9oQVnf"

    connectionString = (
        "mongodb+srv://nguyenhuutrong11133_db_user:"
        "HAU6mfQJYL9oQVnf"
        "@testing.0je3tw5.mongodb.net/"
        "attendance_system"
        "?retryWrites=true&w=majority&appName=testing"
    )

    _client = None

    @classmethod
    def get_collection(cls, name: str):
        if cls._client is None:
            cls._client = MongoClient(
                cls.connectionString,
                serverSelectionTimeoutMS=5000
            )

        db = cls._client["attendance_system"]
        return db[name]