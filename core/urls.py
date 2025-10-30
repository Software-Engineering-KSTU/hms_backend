from django.contrib import admin
from django.urls import path, include, re_path
from rest_framework import permissions
from drf_yasg.views import get_schema_view
from drf_yasg import openapi

# Настройки Swagger
schema_view = get_schema_view(
    openapi.Info(
        title="Hospital Management API",
        default_version='v1',
        description="Документация API для HMS (Hospital Management System)",
        contact=openapi.Contact(email="support@hospital.local"),
        license=openapi.License(name="BSD License"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)

urlpatterns = [
    path('admin/', admin.site.urls),

    # Подключение модулей проекта
    path('api/users/', include('users.urls')),
    path('api/patients/', include('patients.urls')),
    path('api/departments/', include('departments.urls')),
    path('api/staff/', include('staff.urls')),

]

# Swagger и ReDoc
urlpatterns += [
    re_path(r'^swagger(?P<format>\.json|\.yaml)$', schema_view.without_ui(cache_timeout=0), name='schema-json'),
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
    path('redoc/', schema_view.with_ui('redoc', cache_timeout=0), name='schema-redoc'),
]
