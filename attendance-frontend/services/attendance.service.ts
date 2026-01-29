import axiosClient from "./axiosClient";

export const faceAttendance = async (classId: string, image: File) => {
  const formData = new FormData();
  formData.append("class_id", classId);
  formData.append("image", image);

  return axiosClient.post("/attendance/", formData);
};

export const checkStudentCard = async (classId: string, image: File) => {
  const formData = new FormData();
  formData.append("class_id", classId);
  formData.append("image", image);

  return axiosClient.post("/student-card/", formData);
};
