import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:9190/api/v1",
  timeout: 30000,
  withCredentials: false,
});

axiosClient.interceptors.response.use(
  (res) => res,
  (err) => Promise.reject(err),
);

export default axiosClient;
