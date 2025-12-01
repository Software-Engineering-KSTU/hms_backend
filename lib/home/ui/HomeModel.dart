import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/home/dto/DepartmentDto.dart';
import 'package:hmsweb/home/rep/HomeRep.dart';

class HomeModel extends BaseScreenModel {
  final _rep = HomeRep();

  List<DepartmentDto> departments = [];
  String? errorMessage;

  @override
  Future<void> onInitialization() async {
    await loadDepartments();
  }

  Future<void> loadDepartments() async {
    isLoading = true;
    notifyListeners();

    try {
      final newDepartments = await _rep.getDepartments();
      departments = newDepartments;
      errorMessage = null;
    } catch (e) {
      errorMessage = e.toString();
    } finally {
      isLoading = false;
      notifyListeners();
    }
  }
}
