import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/auth/rep/AuthRep.dart';
import 'package:hmsweb/auth/dto/AuthDto.dart';


class AuthModel extends BaseScreenModel {
  final _rep = AuthRep();
  bool isLoading = false;
  String? errorMessage;

  Future<AuthDto?> login(String username, String password) async {
    isLoading = true;
    notifyListeners();
    try {
      return await _rep.login(username: username, password: password);
    } catch (e) {
      errorMessage = e.toString();
      return null;
    } finally {
      isLoading = false;
      notifyListeners();
    }
  }

  Future<AuthDto?> register({
    required String username,
    required String email,
    required String password,
    required String phoneNumber,
    required String address,
    required String dateOfBirth,
  }) async {
    isLoading = true;
    notifyListeners();
    try {
      return await _rep.patientRegister(
        username: username,
        email: email,
        password: password,
        phoneNumber: phoneNumber,
        address: address,
        dateOfBirth: dateOfBirth,
      );
    } catch (e) {
      errorMessage = e.toString();
      return null;
    } finally {
      isLoading = false;
      notifyListeners();
    }
  }

  @override
  Future<void> onInitialization() async {
    // Здесь можно добавить инициализацию, если нужно. Пока оставьте пустым.
  }
}
