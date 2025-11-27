import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/doctor_appointment/dashboard/ui/view/StatusRegistration.dart';

class PatientDashboardScreenModel extends BaseScreenModel {

  final Map<String, StatusRegistration> status = {};

  DateTime focusedDay = DateTime.now();
  DateTime? selectedDay;

  @override
  Future<void> onInitialization() async {

    status.addAll({
      "08:00": StatusRegistration.free,
      "08:20": StatusRegistration.free,
    });
    //init state

  }

  void setMineStatusRegistration() {
    status["08:00"] = StatusRegistration.mine;
    notifyListeners();
  }



}