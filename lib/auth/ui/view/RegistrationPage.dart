import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import 'package:hmsweb/auth/AuthModel.dart';
import 'package:go_router/go_router.dart';

class RegistrationPage extends StatefulWidget {
  const RegistrationPage({super.key});

  @override
  State<RegistrationPage> createState() => _RegistrationPageState();
}

class _RegistrationPageState extends State<RegistrationPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController phoneNumberController = TextEditingController();
  final TextEditingController addressController = TextEditingController();
  final TextEditingController dateOfBirthController = TextEditingController();

  // Ошибки для каждого поля
  String? usernameError;
  String? emailError;
  String? passwordError;
  String? phoneError;
  String? addressError;
  String? dobError;

  bool validate() {
    setState(() {
      usernameError =
      usernameController.text.isEmpty ? "Введите ФИО" : null;

      emailError = emailController.text.isEmpty
          ? "Введите email"
          : (!emailController.text.contains("@")
          ? "Email должен содержать @"
          : null);

      passwordError = passwordController.text.length < 6
          ? "Пароль должен быть не менее 6 символов"
          : null;

      phoneError = phoneNumberController.text.isEmpty
          ? "Введите номер телефона"
          : null;

      addressError =
      addressController.text.isEmpty ? "Введите адрес" : null;

      dobError =
      dateOfBirthController.text.isEmpty ? "Выберите дату рождения" : null;
    });

    return usernameError == null &&
        emailError == null &&
        passwordError == null &&
        phoneError == null &&
        addressError == null &&
        dobError == null;
  }
  bool isValidEmail(String email) {
    final regex = RegExp(r'^[^@]+@[^@]+\.[^@]+$');
    return regex.hasMatch(email);
  }
  @override
  Widget build(BuildContext context) {
    final authModel = Provider.of<AuthModel>(context);

    return Container(
      width: 420,
      padding: const EdgeInsets.symmetric(vertical: 32, horizontal: 24),
      decoration: BoxDecoration(
        color: const Color(0xC6C8F4FF),
        borderRadius: BorderRadius.circular(40),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Text(
            "Регистрация",
            style: TextStyle(fontSize: 22, fontWeight: FontWeight.w600),
          ),
          const SizedBox(height: 20),

          buildField("Введите свое ФИО",
              controller: usernameController, error: usernameError),

          buildField("Введите свою почту",
              controller: emailController, error: emailError),

          buildField("Придумайте пароль",
              controller: passwordController, obscure: true, error: passwordError),

          buildField("Введите свой номер телефона",
              controller: phoneNumberController, error: phoneError),

          buildField("Введите свой адрес",
              controller: addressController, error: addressError),

          buildDateField(context, "Дата рождения",
              controller: dateOfBirthController, error: dobError),

          const SizedBox(height: 20),

          authModel.isLoading
              ? const CircularProgressIndicator()
              : SizedBox(
            width: 200,
            height: 40,
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF76A83B),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(40),
                ),
              ),
              onPressed: () async {
                if (!validate()) return;
                if (!isValidEmail(emailController.text.trim())) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text("Введите корректную почту")),
                  );
                  return;
                }
                //

                final result = await authModel.register(
                  username: usernameController.text,
                  email: emailController.text,
                  password: passwordController.text,
                  phoneNumber: phoneNumberController.text,
                  address: addressController.text,
                  dateOfBirth: dateOfBirthController.text,
                );

                if (result != null) {
                  context.go('/');
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                        content: Text(authModel.errorMessage ??
                            'Ошибка регистрации')),
                  );
                }
              },
              child: const Text("Зарегистрироваться"),
            ),
          ),

          const SizedBox(height: 12),

          GestureDetector(
            onTap: () {
              context.go('/login');
            },
            child: const Text("У вас уже есть аккаунт? Войти"),
          ),
        ],
      ),
    );
  }

  /// ---------------- ПОЛЕ ----------------
  Widget buildField(
      String label, {
        required TextEditingController controller,
        bool obscure = false,
        String? error,
      }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 6),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextField(
            controller: controller,
            obscureText: obscure,
            decoration: InputDecoration(
              hintText: label,
              filled: true,
              fillColor: Colors.white,
              contentPadding:
              const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
              enabledBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(30),
                borderSide: BorderSide(
                    color: error != null ? Colors.red : Colors.green, width: 2),
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(30),
                borderSide: BorderSide(
                    color: error != null ? Colors.red : Colors.green, width: 2),
              ),
            ),
          ),
          if (error != null)
            Padding(
              padding: const EdgeInsets.only(left: 12, top: 3),
              child: Text(error,
                  style: const TextStyle(color: Colors.red, fontSize: 12)),
            ),
        ],
      ),
    );
  }

  /// -------------- ПОЛЕ ДАТЫ ----------------
  Widget buildDateField(
      BuildContext context,
      String label, {
        required TextEditingController controller,
        String? error,
      }) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 6),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextField(
            controller: controller,
            readOnly: true,
            onTap: () async {
              DateTime? picked = await showDatePicker(
                context: context,
                initialDate: DateTime(2000),
                firstDate: DateTime(1900),
                lastDate: DateTime.now(),
              );

              if (picked != null) {
                controller.text =
                    DateFormat('yyyy-MM-dd').format(picked);
              }
            },
            decoration: InputDecoration(
              hintText: label,
              filled: true,
              fillColor: Colors.white,
              contentPadding:
              const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
              enabledBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(30),
                borderSide: BorderSide(
                    color: error != null ? Colors.red : Colors.green, width: 2),
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(30),
                borderSide: BorderSide(
                    color: error != null ? Colors.red : Colors.green, width: 2),
              ),
            ),
          ),
          if (error != null)
            Padding(
              padding: const EdgeInsets.only(left: 12, top: 3),
              child: Text(error,
                  style: const TextStyle(color: Colors.red, fontSize: 12)),
            ),
        ],
      ),
    );
  }
}
//
