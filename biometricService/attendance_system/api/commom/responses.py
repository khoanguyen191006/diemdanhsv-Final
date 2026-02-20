from rest_framework.response import Response
from rest_framework import status


class SuccessResponse(Response):
    def __init__(self, data=None, status_code=status.HTTP_200_OK, message="Success"):
        response_data = {
            "code": status_code,
            "message": message,
            "data": data
        }
        super().__init__(response_data, status=status_code)


class ErrorResponse(Response):
    def __init__(self, message="Error", status_code=status.HTTP_400_BAD_REQUEST, data=None):
        response_data = {
            "code": status_code,
            "message": message,
            "data": data
        }
        super().__init__(response_data, status=status_code)