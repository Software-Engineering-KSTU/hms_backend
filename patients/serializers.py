from rest_framework import serializers
from .models import PatientProfile

class PatientProfileSerializer(serializers.ModelSerializer):
    user_id = serializers.IntegerField(write_only=True)
    user_name = serializers.CharField(source="user.__str__", read_only=True)

    class Meta:
        model = PatientProfile
        fields = [
            "id", "user_id", "user_name", "date_of_birth", "address",
            "emergency_contact_name", "emergency_contact_phone", "medical_history"
        ]
