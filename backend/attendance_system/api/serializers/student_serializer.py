from rest_framework import serializers

class StudentCreateSerializer(serializers.Serializer):
    student_id = serializers.CharField()
    student_name = serializers.CharField()
    class_id = serializers.CharField()
    email = serializers.EmailField(required=False)
