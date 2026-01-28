import axiosClient from "./axiosClient";

export const createClass = (data: {
  class_id: string;
  class_name: string;
  room: string;
  date: string;
  shift: number;
  lecturer_id: string;
  lecturer_name: string;
}) => {
  return axiosClient.post("/classes/", data);
};
