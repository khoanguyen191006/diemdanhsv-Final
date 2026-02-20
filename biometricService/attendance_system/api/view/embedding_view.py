from datetime import datetime

from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser
from rest_framework import status
from rest_framework.response import Response

from attendance_system.service.face_embedding_service import FaceEmbeddingService
from attendance_system.domain.entity.student_embedding_entity import StudentEmbeddingEntity
from attendance_system.utils.mongodb import MongoDB
from attendance_system.api.commom.responses import SuccessResponse, ErrorResponse


class EmbeddingAPIView(APIView):
    parser_classes = [MultiPartParser]

    def post(self, request):
        try:
            student_id_hash = request.data.get("student_id_hash")
            if not student_id_hash:
                return ErrorResponse(
                    message="student_id_hash is required",
                    status_code=status.HTTP_400_BAD_REQUEST
                )

            images = request.FILES.getlist("images")
            if not images:
                return ErrorResponse(
                    message="At least one image is required",
                    status_code=status.HTTP_400_BAD_REQUEST
                )

            try:
                embeddings = FaceEmbeddingService.images_to_embeddings(
                    [img.read() for img in images]
                )
            except ValueError as e:
                return ErrorResponse(
                    message=str(e),
                    status_code=status.HTTP_400_BAD_REQUEST
                )

            collection = MongoDB.get_collection("student_face_embeddings")

            existing_doc = collection.find_one({"student_id_hash": student_id_hash})

            if existing_doc:
                collection.update_one(
                    {"student_id_hash": student_id_hash},
                    {
                        "$push": {"embeddings": {"$each": embeddings}},
                        "$set": {"updated_at": datetime.utcnow()}
                    }
                )
                total_embeddings = len(existing_doc["embeddings"]) + len(embeddings)
                message = f"Added {len(embeddings)} new embeddings to existing student"
            else:
                # Nếu chưa tồn tại, tạo document mới
                entity = StudentEmbeddingEntity(
                    student_id_hash=student_id_hash,
                    embeddings=embeddings,
                    model_version="arcface_v2"
                )
                collection.insert_one(entity.to_dict())
                total_embeddings = len(embeddings)
                message = "Created new student embedding record"

            return SuccessResponse(
                data={
                    "student_id_hash": student_id_hash,
                    "total_embeddings": total_embeddings,
                    "new_embeddings_added": len(embeddings),
                    "model_version": "arcface_v2",
                    "message": message
                },
                status_code=status.HTTP_201_CREATED if not existing_doc else status.HTTP_200_OK
            )

        except Exception as e:
            return ErrorResponse(
                message=f"Internal server error: {str(e)}",
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR
            )