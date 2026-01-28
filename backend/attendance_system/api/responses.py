from rest_framework.response import Response
from rest_framework import status


class SuccessResponse(Response):
    def __init__(
            self,
            data=None,
            message="Success",
            status_code=status.HTTP_200_OK
    ):
        super().__init__({
            "success": True,
            "message": message,
            "data": data
        }, status=status_code)


class ErrorResponse(Response):
    def __init__(
            self,
            message="Error",
            status_code=status.HTTP_400_BAD_REQUEST
    ):
        super().__init__({
            "success": False,
            "message": message,
            "data": None
        }, status=status_code)
