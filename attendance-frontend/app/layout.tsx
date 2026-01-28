import "./globals.css";

export const metadata = {
  title: "AI Attendance System",
  description: "Face & Student Card Attendance",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="vi">
      <body className="bg-gray-100 text-gray-900">{children}</body>
    </html>
  );
}
