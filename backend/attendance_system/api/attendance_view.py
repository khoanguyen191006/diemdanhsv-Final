from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser
from rest_framework import status
from attendance_system.service.attendance_service import AttendanceService
from attendance_system.api.responses import SuccessResponse, ErrorResponse


class AttendanceAPIView(APIView):
    parser_classes = [MultiPartParser]

    def post(self, request):
        try:
            class_id = request.data["class_id"]
            image = request.FILES["image"].read()

            result = AttendanceService.face_attendance(class_id, image)

            return SuccessResponse(
                data=result,
                status_code=status.HTTP_200_OK
            )
        except Exception as e:
            return ErrorResponse(str(e))
