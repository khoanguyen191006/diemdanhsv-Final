"use client";

import { useState } from "react";
import Camera from "@/components/Camera";
import StudentResult from "@/components/StudentResult";
import {
  faceAttendance,
  checkStudentCard,
} from "@/services/attendance.service";

export default function HomePage() {
  const [image, setImage] = useState<File | null>(null);
  const [result, setResult] = useState<any>(null);

  const classId = "INT2201";

  const handleFace = async () => {
    if (!image) return alert("Vui lòng chụp ảnh trước");
    const res = await faceAttendance(classId, image);
    setResult(res.data.data);
  };

  const handleCard = async () => {
    if (!image) return alert("Vui lòng chụp ảnh trước");
    const res = await checkStudentCard(classId, image);
    setResult(res.data.data);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-100 to-slate-200 p-6">
      <h1 className="text-3xl font-bold text-center mb-8 text-slate-800">
        AI Attendance System
      </h1>

      <div className="max-w-6xl mx-auto grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* LEFT - CAMERA */}
        <div className="bg-white rounded-2xl shadow-lg p-4">
          <h2 className="font-semibold text-lg mb-3">📷 Camera</h2>
          <Camera onCapture={setImage} />
        </div>

        {/* RIGHT - ACTION + RESULT */}
        <div className="flex flex-col gap-6">
          {/* SECTION 1 - ACTION */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="font-semibold text-lg mb-4">Thao tác điểm danh</h2>

            <div className="flex gap-3">
              <button
                onClick={handleFace}
                className="flex-1 py-3 rounded-xl bg-purple-600 text-white font-medium hover:bg-purple-700 transition"
              >
                👤 Nhận diện khuôn mặt
              </button>

              <button
                onClick={handleCard}
                className="flex-1 py-3 rounded-xl bg-orange-500 text-white font-medium hover:bg-orange-600 transition"
              >
                💳 Kiểm tra thẻ SV
              </button>
            </div>
          </div>

          {/* SECTION 2 - RESULT */}
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <h2 className="font-semibold text-lg mb-4">Thông tin sinh viên</h2>

            <StudentResult data={result} />
          </div>
        </div>
      </div>
    </div>
  );
}
