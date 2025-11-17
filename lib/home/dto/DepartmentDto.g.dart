// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'DepartmentDto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_DepartmentDto _$DepartmentDtoFromJson(Map<String, dynamic> json) =>
    _DepartmentDto(
      id: (json['id'] as num).toInt(),
      name: json['name'] as String,
    );

Map<String, dynamic> _$DepartmentDtoToJson(_DepartmentDto instance) =>
    <String, dynamic>{'id': instance.id, 'name': instance.name};
