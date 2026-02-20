from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser
from rest_framework import status
from attendance_system.service.verify_face_service import VerifyFaceService
from attendance_system.api.commom.responses import SuccessResponse


class VerifyFaceAPIView(APIView):
    parser_classes = [MultiPartParser]

    def post(self, request):
        image = request.FILES.get("image")

        result = VerifyFaceService.verify(image.read())

        return SuccessResponse(
            data=result,
            status_code=status.HTTP_200_OK
        )