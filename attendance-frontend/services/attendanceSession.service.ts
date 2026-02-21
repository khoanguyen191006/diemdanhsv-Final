import axiosClient from "./axiosClient";

export const createAttendanceSession = (data: {
  classId: string;
  sessionDate: string;
  startTime: string;
  endTime: string;
}) => {
  const payload = {
    classId: data.classId,

    sessionDate: `${data.sessionDate}T00:00:00`,
    startTime: `${data.sessionDate}T${data.startTime}:00`,
    endTime: `${data.sessionDate}T${data.endTime}:00`,
  };

  console.log("📤 AttendanceSession payload", payload);

  return axiosClient.post("/attendanceSession", payload);
};
