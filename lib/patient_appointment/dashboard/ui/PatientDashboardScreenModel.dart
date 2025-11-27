import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/doctor_appointment/dashboard/ui/view/StatusRegistration.dart';

class PatientDashboardScreenModel extends BaseScreenModel {
  final Map<String, StatusRegistration> status = {};

  DateTime focusedDay = DateTime.now();
  DateTime? selectedDay;

  @override
  Future<void> onInitialization() async {
    status.addAll({
      "08:20": StatusRegistration.mine,
      "08:40": StatusRegistration.busy,
    });
    //init state
  }

  void setMineStatusRegistration(String time) {
    if (!status.containsKey(time)) {
      status[time] = StatusRegistration.mine;
    }

    status[time] = StatusRegistration.mine;
    notifyListeners();
  }

}
