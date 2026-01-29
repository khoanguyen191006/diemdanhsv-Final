from django.urls import path
from .student_view import StudentAPIView
from .class_view import ClassAPIView
from .attendance_view import AttendanceAPIView
from .student_card_view import StudentCardAPIView

urlpatterns = [
    path("students/", StudentAPIView.as_view(), name="student"),
    path("classes/", ClassAPIView.as_view(), name="class"),
    path("attendance/", AttendanceAPIView.as_view(), name="class"),
    path("student-card/", StudentCardAPIView.as_view(), name="student-card"),
]
