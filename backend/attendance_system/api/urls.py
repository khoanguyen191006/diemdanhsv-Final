from django.urls import path
from .views import DemoAPIView

urlpatterns = [
    path('api/v1/attendance_system/demo/', DemoAPIView.as_view(), name='demo'),
]
