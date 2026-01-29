import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8000/api/v1",
  timeout: 30000,
  withCredentials: false,
});

axiosClient.interceptors.response.use(
  (res) => res,
  (err) => Promise.reject(err),
);

export default axiosClient;
