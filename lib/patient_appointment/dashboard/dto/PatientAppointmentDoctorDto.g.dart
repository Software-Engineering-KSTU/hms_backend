// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'PatientAppointmentDoctorDto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_PatientAppointmentDoctorDto _$PatientAppointmentDoctorDtoFromJson(
  Map<String, dynamic> json,
) => _PatientAppointmentDoctorDto(
  doctorId: json['doctorId'] as String,
  date: json['date'] as String,
  symptomsDescription: json['symptomsDescription'] as String,
  selfTreatmentMethodsTaken: json['selfTreatmentMethodsTaken'] as String,
);

Map<String, dynamic> _$PatientAppointmentDoctorDtoToJson(
  _PatientAppointmentDoctorDto instance,
) => <String, dynamic>{
  'doctorId': instance.doctorId,
  'date': instance.date,
  'symptomsDescription': instance.symptomsDescription,
  'selfTreatmentMethodsTaken': instance.selfTreatmentMethodsTaken,
};
