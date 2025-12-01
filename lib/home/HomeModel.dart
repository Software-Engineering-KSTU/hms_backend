import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/home/rep/HomeRep.dart';
import 'package:hmsweb/home/dto/DepartmentDto.dart';

class HomeModel extends BaseScreenModel {
  final HomeRep _rep = HomeRep();

  /// Список департаментов, приходящих с бэкенда
  List<DepartmentDto> departments = [];

  /// Ошибки
  String? errorMessage;

  /// Загрузка департаментов
  Future<void> loadDepartments() async {
    isLoading = true;
    notifyListeners();

    try {
      departments = await _rep.getDepartments();
      errorMessage = null;
    } catch (e) {
      errorMessage = e.toString();
    } finally {
      isLoading = false;
      notifyListeners();
    }
  }

  /// Вызывается автоматически в initialize()
  @override
  Future<void> onInitialization() async {
    await loadDepartments();
  }
}
