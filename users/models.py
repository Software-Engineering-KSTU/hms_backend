from django.contrib.auth.models import AbstractUser
from django.db import models

class User(AbstractUser):
    phone_number = models.CharField(max_length=20, unique=True)

    USERNAME_FIELD = 'phone_number'
    REQUIRED_FIELDS = ['username', 'email', 'first_name', 'last_name']

    class Meta:
        db_table = 'users'

    def __str__(self):
        return f"{self.first_name} {self.last_name}"
