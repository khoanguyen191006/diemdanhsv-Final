import axiosClient from "./axiosClient";

export const createClass = (data: {
  className: string;
  room: string;
  startDate: string;
  endDate: string;
}) => {
  return axiosClient.post("/class", data);
};
