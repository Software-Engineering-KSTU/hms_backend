import 'package:freezed_annotation/freezed_annotation.dart';

part 'AuthDto.freezed.dart';
part 'AuthDto.g.dart';

@freezed
abstract class AuthDto with _$AuthDto {
  const factory AuthDto({
    required String accessToken,
    required String refreshToken,
  }) = _AuthDto;

  factory AuthDto.fromJson(Map<String, dynamic> json) =>
      _$AuthDtoFromJson(json);
}