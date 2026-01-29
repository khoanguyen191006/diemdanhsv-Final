from rest_framework.views import APIView
from rest_framework import status
from attendance_system.domain.dto.student_dto import StudentDTO
from attendance_system.service.student_service import StudentService
from attendance_system.api.responses import SuccessResponse, ErrorResponse
from rest_framework.parsers import MultiPartParser, FormParser
from attendance_system.api.serializers.student_serializer import StudentCreateSerializer

class StudentAPIView(APIView):
    parser_classes = [MultiPartParser, FormParser]

    def post(self, request):
        serializer = StudentCreateSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        dto = StudentDTO(**serializer.validated_data)

        images = []
        if "images" in request.FILES:
            images = [f.read() for f in request.FILES.getlist("images")]

        result = StudentService.insert_student(dto, images)

        return SuccessResponse(
            data=result,
            status_code=status.HTTP_201_CREATED
        )


