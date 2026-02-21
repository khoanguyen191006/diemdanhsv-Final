import axiosClient from "./axiosClient";

export const createStudent = (data: {
  studentCode: string;
  fullName: string;
  email: string;
  status: string;
  image?: File;
}) => {
  const formData = new FormData();
  formData.append("studentCode", data.studentCode);
  formData.append("fullName", data.fullName);
  formData.append("email", data.email);
  formData.append("status", data.status);

  if (data.image) {
    formData.append("image", data.image);
  }

  return axiosClient.post("/student", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};
