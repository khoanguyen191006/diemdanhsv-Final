"use client";

import { useState } from "react";
import { createStudent } from "@/services/student.service";

export default function StudentPage() {
  const [form, setForm] = useState<any>({
    images: [],
  });

  const submit = async () => {
    await createStudent(form);
    alert("Thêm sinh viên thành công");
  };

  return (
    <div className="p-6 max-w-md mx-auto space-y-2">
      <h2 className="text-xl font-bold">Thêm sinh viên</h2>

      {["student_id", "student_name", "class_id", "email"].map((f) => (
        <input
          key={f}
          placeholder={f}
          className="border p-2 w-full"
          onChange={(e) => setForm({ ...form, [f]: e.target.value })}
        />
      ))}

      <input
        type="file"
        multiple
        onChange={(e) =>
          setForm({
            ...form,
            images: Array.from(e.target.files || []),
          })
        }
      />

      <button
        onClick={submit}
        className="bg-green-600 text-white px-4 py-2 rounded"
      >
        Thêm sinh viên
      </button>
    </div>
  );
}
