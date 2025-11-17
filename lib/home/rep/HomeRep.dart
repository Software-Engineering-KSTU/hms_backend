import 'package:hmsweb/home/api/HomeApi.dart';
import 'package:hmsweb/home/dto/DepartmentDto.dart';

class HomeRep {
  final _api = HomeApi();

  Future<List<DepartmentDto>> getDepartments() async {
    final response = await _api.getDepartments();

    final List<dynamic> jsonList = response.data;

    return jsonList
        .map((e) => DepartmentDto.fromJson(e as Map<String, dynamic>))
        .toList();
  }
}
