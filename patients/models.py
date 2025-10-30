from django.db import models
from users.models import User

class PatientProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='patient_profile')
    date_of_birth = models.DateField()
    address = models.CharField(max_length=255)
    emergency_contact_name = models.CharField(max_length=100)
    emergency_contact_phone = models.CharField(max_length=20)
    medical_history = models.TextField()

    class Meta:
        db_table = 'patients'

    def __str__(self):
        return self.user.__str__()
