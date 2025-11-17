import 'package:freezed_annotation/freezed_annotation.dart';

part 'DepartmentDto.freezed.dart';
part 'DepartmentDto.g.dart';

@freezed
abstract class DepartmentDto with _$DepartmentDto {
  const factory DepartmentDto({required int id, required String name}) =
      _DepartmentDto;

  factory DepartmentDto.fromJson(Map<String, dynamic> json) =>
      _$DepartmentDtoFromJson(json);
}
