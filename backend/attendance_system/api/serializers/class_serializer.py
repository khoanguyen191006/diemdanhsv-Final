from rest_framework import serializers

class ClassCreateSerializer(serializers.Serializer):
    class_id = serializers.CharField()
    class_name = serializers.CharField()
    room = serializers.CharField()
    date = serializers.DateField()
    shift = serializers.IntegerField()
    lecturer_id = serializers.CharField()
    lecturer_name = serializers.CharField()
