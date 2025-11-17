import 'package:dio/dio.dart';
import 'package:hmsweb/http/HttpRequest.dart';

class HomeApi extends HttpRequest{

  Future<Response> getDepartments() async {
    return await getRequest('api/departments');

  }

}