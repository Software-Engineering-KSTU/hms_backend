import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/patient_appointment/dashboard/dto/DoctorInfoDto.dart';

import '../../rep/PatientDashboardRep.dart';

class DoctorListScreenModel extends BaseScreenModel {

  final _rep = PatientDashboardRep();
  final List<DoctorInfoDto> doctors = List.empty();

  @override
  Future<void> onInitialization() async {
    print(await _rep.fetchDoctors());

    doctors.addAll(await _rep.fetchDoctors());
  }

}