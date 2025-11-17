import 'package:dio/dio.dart';

class HttpRequest {

  static final _options = BaseOptions(
    baseUrl: 'http://localhost:8080/',
    connectTimeout: Duration(seconds: 5),
    receiveTimeout: Duration(seconds: 3),
  );

  final dio = Dio(_options);

  Future<Response> getRequest(String endpoint) async {
    return await dio.get(endpoint);
  }

  Future<Response> postRequest(String endpoint) async {
    return await dio.post(endpoint);
  }

}