from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status



class DemoAPIView(APIView):
    def get(self, request):

        try:

            return Response({
                "success": True,
                "analysis": "demo",
            }, status=status.HTTP_200_OK)

        except Exception as e:
            return Response(
                {"error": str(e)},
                status=status.HTTP_500_INTERNAL_SERVER_ERROR
            )
