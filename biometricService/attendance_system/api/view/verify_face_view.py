from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser
from rest_framework import status

from attendance_system.service.verify_face_service import VerifyFaceService
from attendance_system.api.commom.responses import SuccessResponse, ErrorResponse


class VerifyFaceAPIView(APIView):
    parser_classes = [MultiPartParser]

    def post(self, request):
        image = request.FILES.get("image")

        if not image:
            return ErrorResponse(
                message="Image is required",
                status_code=status.HTTP_400_BAD_REQUEST
            )

        result = VerifyFaceService.verify(image.read())

        if not result.get("found"):
            return ErrorResponse(
                message=result.get("message", "Face not recognized"),
                errors=result,
                status_code=status.HTTP_400_BAD_REQUEST
            )

        return SuccessResponse(
            data=result,
            message="Face verified successfully",
            status_code=status.HTTP_200_OK
        )