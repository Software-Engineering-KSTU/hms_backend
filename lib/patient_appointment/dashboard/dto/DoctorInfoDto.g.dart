// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'DoctorInfoDto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_DoctorInfoDto _$DoctorInfoDtoFromJson(Map<String, dynamic> json) =>
    _DoctorInfoDto(
      doctorId: json['id'] as String,
      doctorName: _userFromJson(json['user'] as Map<String, dynamic>),
      specialization: json['specialization'] as String,
    );

Map<String, dynamic> _$DoctorInfoDtoToJson(_DoctorInfoDto instance) =>
    <String, dynamic>{
      'id': instance.doctorId,
      'user': instance.doctorName,
      'specialization': instance.specialization,
    };
