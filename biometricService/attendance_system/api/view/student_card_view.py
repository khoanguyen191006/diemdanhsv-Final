from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser
from rest_framework import status
from attendance_system.service.student_card_service import StudentCardService
from attendance_system.api.commom.responses import SuccessResponse


class StudentCardAPIView(APIView):
    parser_classes = [MultiPartParser]

    def post(self, request):
        image = request.FILES.get("image")
        result = StudentCardService.read_student_id(image.read())

        return SuccessResponse(
            data=result,
            status_code=status.HTTP_200_OK
        )