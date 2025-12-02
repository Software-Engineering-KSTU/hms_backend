import 'package:dio/dio.dart';

class HttpRequest {
  // Токен объявляем как константу или как поле, если он будет обновляться
  final String myToken = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2NDQ0MjE5NSwiZXhwIjoxNzY0NDQzMDk1fQ.cFIRwrLYz4iuTFx8yx7H_CXsmuHLRRYOR92OdIs-mK8OC7EmpH45MAUquOVS0hneys9xIhWpwL3jIIvJUYZgw';

  // Клиент Dio объявляем с модификатором `late`
  late final Dio dio;

  // Конструктор: Инициализируем dio и добавляем Interceptor
  HttpRequest({String? baseUrl}) : dio = Dio(BaseOptions(
    // Используйте 127.0.0.1:8080 для локальной разработки,
    // если ваш сервер запущен на этом порту.
    baseUrl: baseUrl ?? 'http://127.0.0.1:8080/',
    connectTimeout: const Duration(seconds: 5),
    receiveTimeout: const Duration(seconds: 3),
  )) {
    // Добавляем ваш перехватчик здесь, в теле конструктора
    dio.interceptors.add(AuthInterceptor(myToken));

    // Полезно добавить LogInterceptor для отладки
    dio.interceptors.add(LogInterceptor(
      requestHeader: true,
      requestBody: true,
      responseBody: true,
      error: true,
    ));
  }

  // Методы для запросов
  Future<Response> getRequest(String endpoint) async {
    return await dio.get(endpoint);
  }

  Future<Response> postRequest(String endpoint, {dynamic data}) async {
    return await dio.post(endpoint, data: data);
  }
}
// ------------------------------------------------------------------

class AuthInterceptor extends Interceptor {
  final String token;

  AuthInterceptor(this.token);

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    // В DioInterceptor токен добавляется корректно
    options.headers['Authorization'] = 'Bearer $token';
    super.onRequest(options, handler);
  }
}

class Test extends HttpRequest {

  Future<void> getTest() {
    return getRequest('api/departments');
  }

}