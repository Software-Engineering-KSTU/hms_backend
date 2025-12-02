import 'package:freezed_annotation/freezed_annotation.dart';

part 'PatientAppointmentDoctorDto.freezed.dart';
part 'PatientAppointmentDoctorDto.g.dart';

@freezed
abstract class PatientAppointmentDoctorDto with _$PatientAppointmentDoctorDto {

  const factory PatientAppointmentDoctorDto({
    required String doctorId,
    required String date,
    required String symptomsDescription,
    required String selfTreatmentMethodsTaken
  }) = _PatientAppointmentDoctorDto;

  factory PatientAppointmentDoctorDto.fromJson(Map<String, dynamic> json) =>
      _$PatientAppointmentDoctorDtoFromJson(json);

}