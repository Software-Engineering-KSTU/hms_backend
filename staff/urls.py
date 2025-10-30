from django.urls import path
from .views import (
    StaffListCreateView, StaffDetailView,
    DoctorProfileListCreateView, DoctorProfileDetailView
)

urlpatterns = [
    # Staff
    path('', StaffListCreateView.as_view(), name='staff-list-create'),
    path('<int:pk>/', StaffDetailView.as_view(), name='staff-detail'),

    # Doctor profiles
    path('doctors/', DoctorProfileListCreateView.as_view(), name='doctor-list-create'),
    path('doctors/<int:pk>/', DoctorProfileDetailView.as_view(), name='doctor-detail'),
]
