import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/home/dto/DepartmentDto.dart';
import 'package:hmsweb/home/rep/HomeRep.dart';

class HomeModel extends BaseScreenModel {

  final _rep = HomeRep();
  final List<DepartmentDto> departments = [];

  @override
  Future<void> onInitialization() async {

    final newDepartments = await _rep.getDepartments();

    departments.addAll(newDepartments);
  }

}