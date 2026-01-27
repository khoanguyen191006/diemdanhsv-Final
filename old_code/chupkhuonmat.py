import cv2
import os
import time

DATASET_DIR = "dataset"
NUM_IMAGES = 15
CAMERA_INDEX = 0

def capture_student_images(student_id):
    save_dir = os.path.join(DATASET_DIR, student_id)
    os.makedirs(save_dir, exist_ok=True)

    cam = cv2.VideoCapture(CAMERA_INDEX)

    if not cam.isOpened():
        print("Không mở được camera")
        return

    count = 0
    print(f"Bắt đầu chụp ảnh cho {student_id}")
    print("Nhấn C để chụp | Q để thoát")

    while True:
        ret, frame = cam.read()
        if not ret:
            print("Không đọc được frame")
            break

        cv2.imshow("Capture Dataset", frame)
        key = cv2.waitKey(1) & 0xFF

        if key == ord("c"):
            count += 1
            img_path = os.path.join(save_dir, f"{count}.jpg")
            cv2.imwrite(img_path, frame)
            print(f"Đã lưu {img_path}")

        if key == ord("q") or count >= NUM_IMAGES:
            break

    cam.release()
    cv2.destroyAllWindows()
    print("Hoàn tất chụp ảnh")

if __name__ == "__main__":
    student_id = input("Nhập MSSV (vd: sv001): ")
    capture_student_images(student_id)
