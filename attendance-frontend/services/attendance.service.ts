import axiosClient from "./axiosClient";

export const verifyFace = async (image: File) => {
  const formData = new FormData();
  formData.append("image", image);

  return axiosClient.post("/attendanceRecord/verifyFace", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
