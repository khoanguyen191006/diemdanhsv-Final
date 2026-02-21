import axiosClient from "./axiosClient";

export const createAttendanceRecord = async (
  sessionId: string,
  image: File,
) => {
  const formData = new FormData();
  formData.append("sessionId", sessionId);
  formData.append("image", image);

  return axiosClient.post("/attendanceRecord", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
