from django.urls import path
from .student_view import StudentAPIView
from .class_view import ClassAPIView
from .attendance_view import AttendanceAPIView

urlpatterns = [
    path("api/v1/student/", StudentAPIView.as_view(), name="student"),
    path("api/v1/classes/", ClassAPIView.as_view(), name="class"),
    path("api/v1/ateendance_view/", AttendanceAPIView.as_view(), name="class"),
]
