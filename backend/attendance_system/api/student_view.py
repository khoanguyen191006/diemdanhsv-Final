from rest_framework.views import APIView
from rest_framework import status
from attendance_system.domain.dto.student_dto import StudentDTO
from attendance_system.service.student_service import StudentService
from attendance_system.api.responses import SuccessResponse, ErrorResponse
from rest_framework.parsers import MultiPartParser, FormParser

class StudentAPIView(APIView):
    parser_classes = [MultiPartParser, FormParser]

    def post(self, request):
        dto = StudentDTO(
            student_id=request.data["student_id"],
            student_name=request.data["student_name"],
            class_id=request.data["class_id"],
            email=request.data.get("email")
        )

        images = []
        if "images" in request.FILES:
            images = [f.read() for f in request.FILES.getlist("images")]

        result = StudentService.insert_student(dto, images)

        return SuccessResponse(
            data=result,
            status_code=status.HTTP_201_CREATED
        )


