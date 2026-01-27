import os
import re
import time
import cv2
import unicodedata
import numpy as np
from datetime import datetime

#from ultralytics import YOLO
from easyocr import Reader
from PIL import Image, ImageDraw, ImageFont
from pymongo import MongoClient
from bson import ObjectId
def insert_class():
    # ======================== DATE PARSER ========================
    def parse_date(d):
        from datetime import datetime, date

        if not d:
            return ""
        d = d.strip()

        fmts = ["%Y-%m-%d", "%d/%m/%Y", "%d-%m-%Y"]

        for fmt in fmts:
            try:
                parsed = datetime.strptime(d, fmt).date()

                # ---- KIỂM TRA NGÀY PHẢI ≥ TODAY ----
                if parsed < date.today():
                    print("❗ The date must be today or later!")
                    return parse_date(input("date: "))

                return parsed.strftime("%d-%m-%Y")

            except ValueError:
                pass

        print("❗ Incorrect date format (format: DD/MM/YYYY)")
        return parse_date(input("date: "))

    # ======================== USER INPUT ========================

    result = {}
    class_id = input("class id: ")
    class_name = input("class name: ")
    room = input("room: ")
    date_raw = input("date: ")
    date_norm = parse_date(date_raw)
    if date_norm:
        result["date"] = date_norm

    while True:
        try:
            temp = int(input("1:Ca Sang\n2:Ca Chieu\n3:Ca Toi"))
            if temp> 0 and temp< 4:
                break
            else:
                print("Vui lòng nhập lại!")
        except ValueError:
            print("Không phải số nguyên. Nhập lại!")



    start_raw=None
    end_raw=None
    if temp==1:
        start_raw ="07:15"
        end_raw ="11:00"
    else:
        if temp==2:
            start_raw ="12:30"
            end_raw ="16:00"
        if temp==3:
            start_raw ="17:00"
            end_raw ="20:30"

    lecturer_id = input("lecturer id: ")
    lecturer_name = input("lecturer name: ")
    today = datetime.now().strftime("%d/%m/%Y")

    # ======================== OUTPUT RESULT ========================

    if class_id:
        result["class_id"] = class_id
    if class_name:
        result["class_name"] = class_name
    if lecturer_id:
        result["lecturer_id"] = lecturer_id
    if lecturer_name:
        result["lecturer_name"] = lecturer_name


    # chuẩn hóa giờ

    if start_raw:
        result["start_time"] = start_raw

    if end_raw :
        result["end_time"] = end_raw

    if room:
        result["room"] = room

    return result

def save_to_mongodb(collection, result):
    now = datetime.now()
    doc = {
        "_id": ObjectId(),
        "class_id": result.get("class_id", ""),
        "class_name": result.get("class_name", ""),
        "date_time":{
            "date": now.strftime("%d-%m-%Y"),
            "time": now.strftime("%H:%M:%S"),
        },
        "lecturer_information":{
            "lecturer_id": result.get("lecturer_id", ""),
            "lecturer_name": result.get("lecturer_name", ""),
        },
        "ListOfStudents": {
                "tongsv":0,
                "ds":[],
        },
        "class_information":{
            "date_time": {
                "date": result.get("date", ""),
                "time":{
                    "start": result.get("start_time", ""),
                    "end": result.get("end_time", ""),
                }
            },
            "Room": result.get("room", ""),
        },
    }
    collection.insert_one(doc)
    return doc


# ==== Main ====
def main():
    client = MongoClient("mongodb://localhost:27017/")
    db = client["class"]
    collection = db["class"]


    while True:
        save_to_mongodb(collection, insert_class())

if __name__ == "__main__":
    main()