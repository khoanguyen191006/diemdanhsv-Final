"use client";

export default function StudentResult({ data }: any) {
  if (!data) return null;

  return (
    <div className="mt-4 bg-slate-50 border rounded-xl p-4 space-y-1">
      <p>
        <b>MSSV:</b> {data.student_id}
      </p>
      <p>
        <b>Họ tên:</b> {data.student_name}
      </p>
      <p>
        <b>Email:</b> {data.email}
      </p>
      {data.confidence && (
        <p className="text-green-600 font-semibold">
          Độ tin cậy: {Math.round(data.confidence * 100)}%
        </p>
      )}
    </div>
  );
}
