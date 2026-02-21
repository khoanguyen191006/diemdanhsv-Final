import axiosClient from "./axiosClient";

export const createClassEnrollment = (data: {
  classId: string;
  studentId: string;
}) => {
  return axiosClient.post("/classEnrollment", data);
};
