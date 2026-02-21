"use client";

import { useState } from "react";
import Camera from "@/components/Camera";
import StudentResult from "@/components/StudentResult";
import { createAttendanceRecord } from "@/services/attendanceRecord.service";

export default function HomePage() {
  const [sessionId, setSessionId] = useState("");
  const [image, setImage] = useState<File | null>(null);
  const [result, setResult] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const handleAttendance = async () => {
    if (!sessionId.trim()) {
      alert("Vui lòng nhập sessionId");
      return;
    }

    if (!image) {
      alert("Vui lòng chụp ảnh trước");
      return;
    }

    try {
      setLoading(true);
      setResult(null);

      const res = await createAttendanceRecord(sessionId, image);

      setResult({
        status: "SUCCESS",
        message: res.data.data,
      });
    } catch (err) {
      console.error(err);
      setResult({
        status: "FAILED",
        message: "Xác thực hoặc điểm danh thất bại",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-100 to-slate-200 p-6">
      <h1 className="text-3xl font-bold text-center mb-8 text-slate-800">
        AI Attendance System
      </h1>

      <div className="max-w-5xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* LEFT */}
        <div className="bg-white rounded-2xl shadow-lg p-4">
          <h2 className="font-semibold text-lg mb-3">📷 Camera</h2>

          {/* SESSION ID INPUT */}
          <input
            placeholder="Nhập sessionId"
            value={sessionId}
            onChange={(e) => setSessionId(e.target.value)}
            className="mb-3 w-full border rounded-lg p-2"
          />

          <Camera onCapture={setImage} />
        </div>

        {/* RIGHT */}
        <div className="flex flex-col gap-6">
          {/* ACTION */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="font-semibold text-lg mb-4">Điểm danh buổi học</h2>

            <button
              onClick={handleAttendance}
              disabled={loading}
              className="w-full py-3 rounded-xl bg-purple-600 text-white font-medium hover:bg-purple-700 transition disabled:opacity-60"
            >
              {loading ? "Đang xử lý..." : "👤 Xác thực & Điểm danh"}
            </button>
          </div>

          {/* RESULT */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="font-semibold text-lg mb-4">Kết quả</h2>

            <StudentResult data={result} />
          </div>
        </div>
      </div>
    </div>
  );
}
