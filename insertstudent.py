
from datetime import datetime
from pymongo import MongoClient
client = MongoClient("mongodb://localhost:27017/")
db = client["class"]
collection = db["class"]
def insert_student():
    class_id=input("Enter Class ID: ")

    student_name=input("student name: :")
    student_id=input("student ID: :")
    department=input("department: ")
    majors=input("majors: ")

    result={}
    if student_name:
        result["student_name"]=student_name
    if student_id:
        result["student_id"]=student_id
    if department:
        result["department"]=department
    if majors:
        result["majors"]=majors
    if class_id:
        result["class_id"]=class_id
    return result
def save_to_mongodb(collection, result):
    now = datetime.now()
    doc ={
            "student_name": result.get("student_name", ""),
            "student_id": result.get("student_id", ""),
            "department": result.get("department", ""),
            "majors": result.get("majors", ""),
            "RollCall_time":{
                "check-mssv":0,
                "time_in":None,
                "time_out":None,
                "status":{
                    "dadiemdanh":0,
                    "ditre":0
                },
                "FaceID": 0
            },
    }
    collection.update_one(
        {"class_id": result["class_id"]},
        {"$push": {"ListOfStudents.ds": doc}}
    )

    collection.update_one(
        {"class_id": result["class_id"]},
        {"$inc": {"ListOfStudents.tongsv": 1}}
    )
    return doc


# ==== Main ====
def main():
    while True:
        save_to_mongodb(collection, insert_student())

if __name__ == "__main__":
    main()