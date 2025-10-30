from rest_framework import generics
from .models import Department
from .serializers import DepartmentSerializer

# Получение списка всех отделений и создание нового
class DepartmentListCreateView(generics.ListCreateAPIView):
    queryset = Department.objects.all()
    serializer_class = DepartmentSerializer

# Получение, обновление и удаление конкретного отделения
class DepartmentDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Department.objects.all()
    serializer_class = DepartmentSerializer
