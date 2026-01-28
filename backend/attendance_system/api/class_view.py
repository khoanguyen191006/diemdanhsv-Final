from rest_framework.views import APIView
from rest_framework import status
from attendance_system.domain.dto.class_dto import ClassDTO
from attendance_system.service.class_service import ClassService
from attendance_system.api.responses import SuccessResponse, ErrorResponse


class ClassAPIView(APIView):

    def post(self, request):
        try:
            dto = ClassDTO(
                class_id=request.data["class_id"],
                class_name=request.data["class_name"],
                room=request.data["room"],
                date=request.data["date"],
                shift=int(request.data["shift"]),
                lecturer_id=request.data["lecturer_id"],
                lecturer_name=request.data["lecturer_name"],
            )

            result = ClassService.insert_class(dto)
            return SuccessResponse(
                data=result,
                status_code=status.HTTP_201_CREATED
            )

        except Exception as e:
            return ErrorResponse(str(e))
