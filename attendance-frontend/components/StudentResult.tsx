"use client";

export default function StudentResult({ data }: { data: any }) {
  if (!data) {
    return <p className="text-slate-500 italic">Chưa thực hiện điểm danh</p>;
  }

  if (data.status === "FAILED") {
    return (
      <div className="bg-red-50 border border-red-300 rounded-xl p-4 text-red-700">
        ❌ {data.message}
      </div>
    );
  }

  return (
    <div className="bg-green-50 border border-green-300 rounded-xl p-4 text-green-700">
      ✅ Điểm danh thành công
    </div>
  );
}
