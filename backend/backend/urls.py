from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('attendance_system/', include('attendance_system.api.urls')),
]
