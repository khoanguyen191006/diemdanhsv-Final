from django.urls import path
from attendance_system.api.view.embedding_view import EmbeddingAPIView
from attendance_system.api.view.student_card_view import StudentCardAPIView
from attendance_system.api.view.verify_face_view import VerifyFaceAPIView

urlpatterns = [
    path("embeddingImage", EmbeddingAPIView.as_view()),
    path("verifyCard", StudentCardAPIView.as_view()),
    path("verifyFace", VerifyFaceAPIView.as_view()),
]