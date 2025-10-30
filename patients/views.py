from rest_framework import generics, permissions
from .models import PatientProfile
from .serializers import PatientProfileSerializer

class PatientListCreateView(generics.ListCreateAPIView):
    queryset = PatientProfile.objects.all()
    serializer_class = PatientProfileSerializer
    permission_classes = [permissions.IsAuthenticated]

    def perform_create(self, serializer):
        # Пользователь указывается через user_id в теле запроса
        serializer.save()

class PatientRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    queryset = PatientProfile.objects.all()
    serializer_class = PatientProfileSerializer
    permission_classes = [permissions.IsAuthenticated]
