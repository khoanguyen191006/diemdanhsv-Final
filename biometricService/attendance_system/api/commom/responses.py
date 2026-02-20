from rest_framework.response import Response
from rest_framework import status


class SuccessResponse(Response):
    def __init__(self, data=None, status_code=status.HTTP_200_OK, **kwargs):
        response_data = {
            "success": True,
            "data": data,
            "message": kwargs.get("message", "Success")
        }
        super().__init__(response_data, status=status_code)


class ErrorResponse(Response):
    def __init__(self, message="Error", status_code=status.HTTP_400_BAD_REQUEST, errors=None):
        response_data = {
            "success": False,
            "message": message,
            "errors": errors or {}
        }
        super().__init__(response_data, status=status_code)