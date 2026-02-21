"use client";

import { useRef, useEffect } from "react";

export default function Camera({
  onCapture,
}: {
  onCapture: (file: File) => void;
}) {
  const videoRef = useRef<HTMLVideoElement>(null);

  useEffect(() => {
    startCamera();
    return () => {
      const stream = videoRef.current?.srcObject as MediaStream;
      stream?.getTracks().forEach((t) => t.stop());
    };
  }, []);

  const startCamera = async () => {
    const stream = await navigator.mediaDevices.getUserMedia({
      video: { facingMode: "user" },
    });

    if (videoRef.current) {
      videoRef.current.srcObject = stream;
    }
  };

  const capture = () => {
    const video = videoRef.current!;
    const canvas = document.createElement("canvas");

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    const ctx = canvas.getContext("2d")!;
    ctx.drawImage(video, 0, 0);

    canvas.toBlob((blob) => {
      if (!blob) return;

      const file = new File([blob], "face.jpg", {
        type: "image/jpeg",
      });

      onCapture(file);
    }, "image/jpeg");
  };

  return (
    <div className="space-y-3">
      <video
        ref={videoRef}
        autoPlay
        playsInline
        className="rounded-xl border w-full"
      />

      <button
        onClick={capture}
        className="w-full py-2 rounded-lg bg-green-600 text-white hover:bg-green-700 transition"
      >
        📸 Chụp ảnh
      </button>
    </div>
  );
}
