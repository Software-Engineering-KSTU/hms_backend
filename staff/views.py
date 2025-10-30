from rest_framework import generics
from .models import Staff, DoctorProfile
from .serializers import StaffSerializer, DoctorProfileSerializer


# ===== Staff CRUD =====
class StaffListCreateView(generics.ListCreateAPIView):
    queryset = Staff.objects.all()
    serializer_class = StaffSerializer


class StaffDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Staff.objects.all()
    serializer_class = StaffSerializer


# ===== DoctorProfile CRUD =====
class DoctorProfileListCreateView(generics.ListCreateAPIView):
    queryset = DoctorProfile.objects.all()
    serializer_class = DoctorProfileSerializer


class DoctorProfileDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = DoctorProfile.objects.all()
    serializer_class = DoctorProfileSerializer
