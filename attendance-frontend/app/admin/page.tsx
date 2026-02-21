"use client";

import { useEffect, useState } from "react";
import { createClass } from "@/services/class.service";
import { createAttendanceSession } from "@/services/attendanceSession.service";
import { createStudent } from "@/services/student.service";
import { createClassEnrollment } from "@/services/classEnrollment.service";

type Tab = "class" | "session" | "student" | "enrollment";
type Status = "idle" | "loading" | "success" | "error";

export default function ManagementPage() {
  const [tab, setTab] = useState<Tab>("class");

  const [classForm, setClassForm] = useState<any>({});
  const [sessionForm, setSessionForm] = useState<any>({});
  const [studentForm, setStudentForm] = useState<any>({});
  const [enrollForm, setEnrollForm] = useState<any>({});

  const [status, setStatus] = useState<Status>("idle");
  const [message, setMessage] = useState("");

  /* ===== AUTO CLEAR MESSAGE ===== */
  useEffect(() => {
    if (status === "success" || status === "error") {
      const t = setTimeout(() => {
        setStatus("idle");
        setMessage("");
      }, 3000);
      return () => clearTimeout(t);
    }
  }, [status]);

  return (
    <div className="p-6 max-w-xl mx-auto space-y-4">
      {/* ===== TABS ===== */}
      <div className="flex gap-2">
        {[
          ["class", "Tạo Class"],
          ["session", "Tạo buổi học"],
          ["student", "Tạo Sinh Viên"],
          ["enrollment", "Ghi Danh"],
        ].map(([k, label]) => (
          <button
            key={k}
            onClick={() => setTab(k as Tab)}
            className={`px-3 py-1 rounded ${
              tab === k ? "bg-blue-600 text-white" : "bg-gray-200"
            }`}
          >
            {label}
          </button>
        ))}
      </div>

      {/* ===== STATUS MESSAGE ===== */}
      {status !== "idle" && (
        <div
          className={`p-2 rounded text-sm ${
            status === "success"
              ? "bg-green-100 text-green-700"
              : status === "error"
                ? "bg-red-100 text-red-700"
                : "bg-blue-100 text-blue-700"
          }`}
        >
          {message}
        </div>
      )}

      {/* ===== TAB: CLASS ===== */}
      {tab === "class" && (
        <>
          <h2 className="font-bold">Tạo Class</h2>

          <input
            placeholder="Tên lớp"
            className="border p-2 w-full"
            onChange={(e) =>
              setClassForm({ ...classForm, className: e.target.value })
            }
          />

          <input
            placeholder="Phòng học"
            className="border p-2 w-full"
            onChange={(e) =>
              setClassForm({ ...classForm, room: e.target.value })
            }
          />

          <label className="text-sm text-gray-600">Thời gian bắt đầu</label>
          <input
            type="datetime-local"
            className="border p-2 w-full"
            onChange={(e) =>
              setClassForm({ ...classForm, startDate: e.target.value })
            }
          />

          <label className="text-sm text-gray-600">Thời gian kết thúc</label>
          <input
            type="datetime-local"
            className="border p-2 w-full"
            onChange={(e) =>
              setClassForm({ ...classForm, endDate: e.target.value })
            }
          />

          <button
            disabled={status === "loading"}
            onClick={async () => {
              try {
                setStatus("loading");
                setMessage("Đang tạo class...");
                await createClass(classForm);
                setStatus("success");
                setMessage("🎉 Tạo class thành công");
                setClassForm({});
              } catch (e: any) {
                setStatus("error");
                setMessage(
                  e?.response?.data?.message || "❌ Tạo class thất bại",
                );
              }
            }}
            className="bg-green-600 text-white px-4 py-2 rounded disabled:opacity-50"
          >
            {status === "loading" ? "Đang xử lý..." : "Tạo Class"}
          </button>
        </>
      )}

      {/* ===== TAB: SESSION ===== */}
      {tab === "session" && (
        <>
          <h2 className="font-bold">Tạo Session</h2>

          {/* CLASS ID */}
          <input
            placeholder="Class ID"
            className="border p-2 w-full"
            value={sessionForm.classId || ""}
            onChange={(e) =>
              setSessionForm({ ...sessionForm, classId: e.target.value })
            }
          />

          {/* SESSION DATE */}
          <label className="text-sm text-gray-600">Ngày học</label>
          <input
            type="date"
            className="border p-2 w-full"
            value={sessionForm.sessionDate || ""}
            onChange={(e) =>
              setSessionForm({ ...sessionForm, sessionDate: e.target.value })
            }
          />

          {/* START TIME */}
          <label className="text-sm text-gray-600">Giờ bắt đầu</label>
          <input
            type="time"
            className="border p-2 w-full"
            value={sessionForm.startTime || ""}
            onChange={(e) =>
              setSessionForm({ ...sessionForm, startTime: e.target.value })
            }
          />

          {/* END TIME */}
          <label className="text-sm text-gray-600">Giờ kết thúc</label>
          <input
            type="time"
            className="border p-2 w-full"
            value={sessionForm.endTime || ""}
            onChange={(e) =>
              setSessionForm({ ...sessionForm, endTime: e.target.value })
            }
          />

          <button
            onClick={async () => {
              try {
                setStatus("loading");
                setMessage("Đang tạo session...");

                await createAttendanceSession(sessionForm);

                setStatus("success");
                setMessage("✅ Tạo session thành công");
                setSessionForm({});
              } catch (e: any) {
                setStatus("error");
                setMessage(
                  e?.response?.data?.message || "❌ Tạo session thất bại",
                );
              }
            }}
            className="bg-green-600 text-white px-4 py-2 rounded"
          >
            Tạo Session
          </button>
        </>
      )}

      {/* ===== TAB: STUDENT ===== */}
      {tab === "student" && (
        <>
          <h2 className="font-bold">Tạo Sinh Viên</h2>

          {["studentCode", "fullName", "email", "status"].map((f) => (
            <input
              key={f}
              placeholder={f}
              className="border p-2 w-full"
              onChange={(e) =>
                setStudentForm({ ...studentForm, [f]: e.target.value })
              }
            />
          ))}

          <input
            type="file"
            onChange={(e) =>
              setStudentForm({
                ...studentForm,
                image: e.target.files?.[0],
              })
            }
          />

          <button
            onClick={async () => {
              try {
                setStatus("loading");
                setMessage("Đang tạo sinh viên...");
                await createStudent(studentForm);
                setStatus("success");
                setMessage("🎓 Tạo sinh viên thành công");
                setStudentForm({});
              } catch (e: any) {
                setStatus("error");
                setMessage(
                  e?.response?.data?.message || "❌ Tạo sinh viên thất bại",
                );
              }
            }}
            className="bg-green-600 text-white px-4 py-2 rounded"
          >
            Tạo Sinh Viên
          </button>
        </>
      )}

      {/* ===== TAB: ENROLLMENT ===== */}
      {tab === "enrollment" && (
        <>
          <h2 className="font-bold">Ghi Danh Sinh Viên</h2>

          {["classId", "studentId"].map((f) => (
            <input
              key={f}
              placeholder={f}
              className="border p-2 w-full"
              onChange={(e) =>
                setEnrollForm({ ...enrollForm, [f]: e.target.value })
              }
            />
          ))}

          <button
            onClick={async () => {
              try {
                setStatus("loading");
                setMessage("Đang ghi danh...");
                await createClassEnrollment(enrollForm);
                setStatus("success");
                setMessage("📚 Ghi danh thành công");
                setEnrollForm({});
              } catch (e: any) {
                setStatus("error");
                setMessage(
                  e?.response?.data?.message || "❌ Ghi danh thất bại",
                );
              }
            }}
            className="bg-green-600 text-white px-4 py-2 rounded"
          >
            Ghi Danh
          </button>
        </>
      )}
    </div>
  );
}
