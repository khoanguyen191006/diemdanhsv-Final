import axiosClient from "./axiosClient";

export const createStudent = async (data: {
  student_id: string;
  student_name: string;
  class_id: string;
  email: string;
  images: File[];
}) => {
  const formData = new FormData();
  formData.append("student_id", data.student_id);
  formData.append("student_name", data.student_name);
  formData.append("class_id", data.class_id);
  formData.append("email", data.email);

  data.images.forEach((img) => formData.append("images", img));

  return axiosClient.post("/student/", formData);
};
