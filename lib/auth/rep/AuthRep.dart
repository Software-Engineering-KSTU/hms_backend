import 'package:hmsweb/auth/api/AuthApi.dart';
import 'package:hmsweb/auth/dto/AuthDto.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:dio/dio.dart';

class AuthRep {
  final _api = AuthApi();
  static const storage = FlutterSecureStorage();

  Future<AuthDto> patientRegister({
    required String username,
    required String email,
    required String password,
    required String phoneNumber,
    required String address,
    required String dateOfBirth,
  }) async {

    print("REG DATA → username=$username, email=$email, password=$password, phone=$phoneNumber, address=$address, dob=$dateOfBirth");

    try {
      final response = await _api.patientRegister(
        username: username,
        email: email,
        password: password,
        phoneNumber: phoneNumber,
        address: address,
        dateOfBirth: dateOfBirth,
      );

      print("SERVER RESPONSE → ${response.data}");

      final data = response.data as Map<String, dynamic>;
      final authDto = AuthDto.fromJson(data);

      await _saveTokens(authDto);
      return authDto;

    } on DioException catch (e) {
      print("SERVER ERROR → ${e.response?.data}");
      print("STATUS CODE → ${e.response?.statusCode}");
      print("DIO MESSAGE → ${e.message}");
      rethrow; // пробрасываем дальше в AuthModel
    }
  }

  Future<AuthDto> login({
    required String username,
    required String password,
  }) async {

    print("LOGIN DATA → username=$username, password=$password");

    try {
      final response = await _api.login(username: username, password: password);

      print("SERVER RESPONSE → ${response.data}");

      final data = response.data as Map<String, dynamic>;
      final authDto = AuthDto.fromJson(data);

      await _saveTokens(authDto);
      return authDto;

    } on DioException catch (e) {
      print("SERVER ERROR → ${e.response?.data}");
      print("STATUS CODE → ${e.response?.statusCode}");
      rethrow;
    }
  }

  Future<AuthDto> refreshToken() async {
    final refreshToken = await storage.read(key: 'refreshToken');
    if (refreshToken == null) throw Exception('Нет токена обновления');

    try {
      final response = await _api.refreshToken(refreshToken);

      print("REFRESH RESPONSE → ${response.data}");

      final data = response.data as Map<String, dynamic>;
      final authDto = AuthDto.fromJson(data);

      await _saveTokens(authDto);
      return authDto;

    } on DioException catch (e) {
      print("REFRESH ERROR → ${e.response?.data}");
      print("STATUS CODE → ${e.response?.statusCode}");
      rethrow;
    }
  }

  Future<void> _saveTokens(AuthDto authDto) async {
    await storage.write(key: 'accessToken', value: authDto.accessToken);
    await storage.write(key: 'refreshToken', value: authDto.refreshToken);
  }
}
