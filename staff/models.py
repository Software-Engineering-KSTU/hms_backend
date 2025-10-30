from django.db import models
from users.models import User
from departments.models import Department
from specializations.models import Specialization

class Staff(models.Model):
    ROLE_CHOICES = [
        ('doctor', 'Врач'),
        ('manager', 'Менеджер'),
        ('nurse', 'Медсестра'),
        ('admin', 'Администратор'),
    ]

    id = models.AutoField(primary_key=True)
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='staff_profile')
    role = models.CharField(max_length=20, choices=ROLE_CHOICES)
    hire_date = models.DateField()
    department = models.ForeignKey(Department, on_delete=models.SET_NULL, null=True, blank=True)
    position = models.CharField(max_length=100, blank=True, null=True)  # можно заменить на FK в отдельной таблице, если нужно

    class Meta:
        db_table = 'staff'

    def __str__(self):
        return f"{self.user} ({self.role})"


class DoctorProfile(models.Model):
    staff = models.OneToOneField(Staff, on_delete=models.CASCADE, related_name='doctor_profile')
    bio = models.TextField()
    experience_years = models.PositiveIntegerField()
    specializations = models.ManyToManyField(Specialization)

    class Meta:
        db_table = 'doctors'

    def __str__(self):
        return f"Dr. {self.staff.user.last_name}"
