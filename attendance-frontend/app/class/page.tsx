"use client";

import { useState } from "react";
import { createClass } from "@/services/class.service";

export default function ClassPage() {
  const [form, setForm] = useState<any>({});

  const submit = async () => {
    await createClass(form);
    alert("Tạo lớp thành công");
  };

  return (
    <div className="p-6 max-w-md mx-auto space-y-2">
      <h2 className="text-xl font-bold">Tạo lớp</h2>

      {[
        "class_id",
        "class_name",
        "room",
        "date",
        "shift",
        "lecturer_id",
        "lecturer_name",
      ].map((f) => (
        <input
          key={f}
          placeholder={f}
          className="border p-2 w-full"
          onChange={(e) => setForm({ ...form, [f]: e.target.value })}
        />
      ))}

      <button
        onClick={submit}
        className="bg-blue-600 text-white px-4 py-2 rounded"
      >
        Tạo lớp
      </button>
    </div>
  );
}
