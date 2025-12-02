import 'package:hmsweb/doctor_appointment/dashboard/ui/view/StatusRegistration.dart';
import 'package:hmsweb/patient_appointment/dashboard/api/PatientDashboardApi.dart';
import 'package:hmsweb/patient_appointment/dashboard/dto/DoctorInfoDto.dart';
import 'package:hmsweb/patient_appointment/dashboard/dto/PatientAppointmentDoctorDto.dart'
    show PatientAppointmentDoctorDto;

class PatientDashboardRep {
  final _api = PatientDashboardApi();

  Future<List<Map<String, StatusRegistration>>> getDoctorAppointments({
    required String doctorId,
    required String date
}) async {
    final response = await _api.fetchDoctorAppointments(
        doctorId: doctorId,
        date: date
    );

    final List<Map<String, dynamic>> jsonList = response.data;

    for (var element in jsonList) {
      if (element['busyStatus'] == 'other') {
        element['busyStatus'] == StatusRegistration.busy;
      }

      if (element['busyStatus'] == 'mine') {
        element['busyStatus'] == StatusRegistration.mine;
      }

      element['dateTime'] = getTime(element['dateTime']);
    }

    return jsonList as List<Map<String, StatusRegistration>>;
  }

  Future<void> postPatientAppointment(
    PatientAppointmentDoctorDto patientAppointment,
  ) async {

    final patientAppointmentWithFormat = PatientAppointmentDoctorDto(
        doctorId: patientAppointment.doctorId,
        date: getDateBackendFormat(patientAppointment.date),
        symptomsDescription: patientAppointment.symptomsDescription,
        selfTreatmentMethodsTaken: patientAppointment.selfTreatmentMethodsTaken);

    await _api.postPatientAppointment(patientAppointmentWithFormat);
  }

  Future<List<DoctorInfoDto>> fetchDoctors() async {
    final response = await _api.fetchDoctors();

    final List<dynamic> jsonList = response.data;

    return jsonList
        .map((e) => DoctorInfoDto.fromJson(e as Map<String, dynamic>))
        .toList();

  }

}

String getTime(String isoString) {
  String iso = "2025-11-29T13:55:43.219Z";
  DateTime dt = DateTime.parse(iso);

  String time =
      "${dt.hour.toString().padLeft(2, '0')}:${dt.minute.toString().padLeft(2, '0')}";

  return time;
}

String getDateBackendFormat(String time) {
  DateTime now = DateTime.now();

  int hour = int.parse(time.split(":")[0]);
  int minute = int.parse(time.split(":")[1]);

  DateTime result = DateTime(now.year, now.month, now.day, hour, minute);

  String iso = "${result.toIso8601String()}Z";

  return iso;
}
