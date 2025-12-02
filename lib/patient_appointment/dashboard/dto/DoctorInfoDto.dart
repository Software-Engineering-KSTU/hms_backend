import 'package:freezed_annotation/freezed_annotation.dart';

part 'DoctorInfoDto.freezed.dart';
part 'DoctorInfoDto.g.dart';

String _userFromJson(Map<String, dynamic> userJson) {
  return userJson['username'] as String;
}

@freezed
abstract class DoctorInfoDto with _$DoctorInfoDto {

  const factory DoctorInfoDto({
    @JsonKey(name: 'id')
    required String doctorId,
    @JsonKey(name: 'user', fromJson: _userFromJson)
    required String doctorName,
    required String specialization
  }) = _DoctorInfoDto;

  factory DoctorInfoDto.fromJson(Map<String, dynamic> json) =>
      _$DoctorInfoDtoFromJson(json);

}