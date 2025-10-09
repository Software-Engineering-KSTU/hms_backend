from django.db import models

class User(models.Model):
    id = models.AutoField(primary_key=True)
    first_name = models.CharField(max_length=100, blank=False, null=False)
    last_name = models.CharField(max_length=100, blank=False, null=False)
    email = models.EmailField(unique=True, blank=False, null=False)
    phone_number = models.CharField(max_length=20, blank=False, null=False)

    class Meta:
        db_table = 'users'

class Specialization(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=100, unique=True, blank=False, null=False)
    description = models.TextField(blank=False, null=False)

    class Meta:
        db_table = 'specializations'

class Doctor(models.Model):
    id = models.AutoField(primary_key=True)
    bio = models.TextField(blank=False, null=False)
    experience_years = models.PositiveIntegerField(blank=False, null=False)
    hire_date = models.DateField(verbose_name='Дата приёма на работу')

    user = models.OneToOneField(User, on_delete=models.CASCADE)
    specializations = models.ManyToManyField(Specialization)

    class Meta:
        db_table = 'doctors'

class Patient(models.Model):
    id = models.AutoField(primary_key=True)
    date_of_birth = models.DateField(blank=False, null=False)
    address = models.CharField(max_length=255, blank=False, null=False)
    emergency_contact_name = models.CharField(max_length=100, blank=False, null=False)
    emergency_contact_phone = models.CharField(max_length=20, blank=False, null=False)
    medical_history = models.TextField(blank=False, null=False, verbose_name='Медицинская история')

    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='patient_profile')

    class Meta:
        db_table = 'patients'

class Position(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=100, unique=True, blank=False, null=False)
    description = models.TextField(blank=True, null=True)

    class Meta:
        db_table = 'positions'

class Manager(models.Model):
    id = models.AutoField(primary_key=True)
    hire_date = models.DateField(verbose_name='Дата приёма на работу')

    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='manager_profile')
    position = models.ForeignKey(Position, on_delete=models.CASCADE, null=False)

    class Meta:
        db_table = 'managers'