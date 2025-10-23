from rest_framework import generics
from .models import Specialization
from .serializers import SpecializationSerializer


class SpecializationListCreateView(generics.ListCreateAPIView):
    queryset = Specialization.objects.all()
    serializer_class = SpecializationSerializer


class SpecializationDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Specialization.objects.all()
    serializer_class = SpecializationSerializer
