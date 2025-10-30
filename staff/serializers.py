from rest_framework import serializers
from .models import Staff, DoctorProfile
from users.serializers import UserSerializer
from departments.serializers import DepartmentSerializer
from specializations.serializers import SpecializationSerializer

class StaffSerializer(serializers.ModelSerializer):
    user = UserSerializer(read_only=True)
    department = DepartmentSerializer(read_only=True)

    class Meta:
        model = Staff
        fields = ['id', 'user', 'role', 'hire_date', 'department', 'position']


class DoctorProfileSerializer(serializers.ModelSerializer):
    staff = StaffSerializer(read_only=True)
    specializations = SpecializationSerializer(many=True, read_only=True)

    class Meta:
        model = DoctorProfile
        fields = ['id', 'staff', 'bio', 'experience_years', 'specializations']