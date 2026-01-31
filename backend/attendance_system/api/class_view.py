from rest_framework.views import APIView
from rest_framework import status

from attendance_system.domain.dto.class_dto import ClassDTO
from attendance_system.service.class_service import ClassService
from attendance_system.api.responses import SuccessResponse
from attendance_system.api.serializers.class_serializer import ClassCreateSerializer


class ClassAPIView(APIView):

    def post(self, request):
        serializer = ClassCreateSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        dto = ClassDTO(**serializer.validated_data)

        result = ClassService.insert_class(dto)

        return SuccessResponse(
            data=result,
            status_code=status.HTTP_201_CREATED
        )
