import 'package:dio/dio.dart';  // Если используете dio, как в HomeApi
import 'package:hmsweb/http/HttpRequest.dart';  // Глобальный HttpRequest

class AuthApi extends HttpRequest {

  Future<Response> patientRegister({
    required String username,
    required String email,
    required String password,
    required String phoneNumber,
    required String address,
    required String dateOfBirth,
  }) async {



    return await postRequest('api/auth/patient-register', data: {
      'username': username,
      'email': email,
      'password': password,
      'phoneNumber': phoneNumber,
      'address': address,
      'dateOfBirth': dateOfBirth,
    });
  }

  Future<Response> login({
    required String username,
    required String password,
  }) async {
    return await postRequest('api/auth/login', data: {
      'username': username,
      'password': password,
    });
  }

  Future<Response> refreshToken(String refreshToken) async {
    return await postRequest('api/auth/refresh-token', headers: {
      'Authorization': 'Bearer $refreshToken',
    });
  }
}





// import 'package:dio/dio.dart';
// import 'package:hmsweb/http/HttpRequest.dart';
//
// class AuthApi extends HttpRequest {
//
//   Future<Response> patientRegister({
//     required String username,
//     required String email,
//     required String password,
//     required String phoneNumber,
//     required String address,
//     required String dateOfBirth,
//   }) async {
//
//     // ЛОГИ ПЕРЕД ЗАПРОСОМ
//     print("REG DATA: $username | $email | $password | $phoneNumber | $address | $dateOfBirth");
//
//     try {
//       final response = await postRequest(
//         'api/auth/patient-register',
//         data: {
//           'username': username,
//           'email': email,
//           'password': password,
//           'phoneNumber': phoneNumber,
//           'address': address,
//           'dateOfBirth': dateOfBirth,
//         },
//       );
//
//       print("SERVER SUCCESS RESPONSE: ${response.data}");
//       return response;
//
//     } on DioException catch (e) {
//       print("------ SERVER ERROR ------");
//
//       print("STATUS CODE: ${e.response?.statusCode}");
//       print("SERVER RESPONSE: ${e.response?.data}");
//       print("REQUEST BODY: ${e.requestOptions.data}");
//       print("REQUEST PATH: ${e.requestOptions.uri}");
//
//       print("---------------------------");
//
//       rethrow;
//     }
//   }
//
//   @override
//   Future<Response> login({
//     required String username,
//     required String password,
//   }) async {
//     return await postRequest('api/auth/login', data: {
//       'username': username,
//       'password': password,
//     });
//   }
//
//   @override
//   Future<Response> refreshToken(String refreshToken) async {
//     return await postRequest('api/auth/refresh-token', headers: {
//       'Authorization': 'Bearer $refreshToken',
//     });
//   }
// }
