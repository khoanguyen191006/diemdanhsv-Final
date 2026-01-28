import axiosClient from "./axiosClient";

export const faceAttendance = async (classId: string, image: File) => {
  const formData = new FormData();
  formData.append("class_id", classId);
  formData.append("image", image);

  return axiosClient.post("/ateendance_view/", formData);
};

export const checkStudentCard = async (classId: string, image: File) => {
  const formData = new FormData();
  formData.append("class_id", classId);
  formData.append("image", image);

  return axiosClient.post("/student_card/", formData);
};
