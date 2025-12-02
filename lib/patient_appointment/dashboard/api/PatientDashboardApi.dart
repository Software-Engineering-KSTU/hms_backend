// post current day or other for init
// convert json to Map<String, StatusRegistration>

// make convert  Map<String, StatusRegistration> to json if was selected time of day
// then post new data to backend

// add new task for http code
// setting sign in and up

import 'package:dio/dio.dart';
import 'package:hmsweb/http/HttpRequest.dart';
import 'package:hmsweb/patient_appointment/dashboard/dto/PatientAppointmentDoctorDto.dart';

class PatientDashboardApi extends HttpRequest {
  Future<Response> fetchDoctorAppointments( {
    required String doctorId,
    required String date,
  }) {
    return getRequest('api/appointments/slots/1'); //TODO
  }

  Future<Response> postPatientAppointment(
    PatientAppointmentDoctorDto patientAppointment,
  ) {
    return postRequest('api/appointments/register');
  }

  Future<Response> fetchDoctors() {
    return postRequest('api/appointments/doctors');
  }
}
