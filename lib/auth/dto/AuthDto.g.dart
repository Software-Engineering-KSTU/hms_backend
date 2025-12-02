// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'AuthDto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_AuthDto _$AuthDtoFromJson(Map<String, dynamic> json) => _AuthDto(
  accessToken: json['accessToken'] as String,
  refreshToken: json['refreshToken'] as String,
);

Map<String, dynamic> _$AuthDtoToJson(_AuthDto instance) => <String, dynamic>{
  'accessToken': instance.accessToken,
  'refreshToken': instance.refreshToken,
};
