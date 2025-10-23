from django.urls import path
from .views import SpecializationListCreateView, SpecializationDetailView

urlpatterns = [
    path('', SpecializationListCreateView.as_view(), name='specialization-list-create'),
    path('<int:pk>/', SpecializationDetailView.as_view(), name='specialization-detail'),
]
