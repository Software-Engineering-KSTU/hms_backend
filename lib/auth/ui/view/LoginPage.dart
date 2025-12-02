import 'package:flutter/material.dart';
import 'package:provider/provider.dart';  // Если используете Provider для модели; иначе адаптируйте
import 'package:hmsweb/auth/AuthModel.dart';  // Импорт модели (относительный путь)
import 'package:go_router/go_router.dart';  // Добавьте импорт

class LoginPage extends StatelessWidget {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    final authModel = Provider.of<AuthModel>(context);  // Пример с Provider; адаптируйте под ваш state management

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
            "Авторизация",
            style: TextStyle(fontSize: 22, fontWeight: FontWeight.w600),
          ),
          const SizedBox(height: 20),

          customField("Введите свой username"),
          customField("Введите свой пароль", obscure: true),

          const SizedBox(height: 20),

          if (authModel.isLoading)
            const CircularProgressIndicator()  // Индикатор загрузки
          else
            SizedBox(
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
                  final result = await authModel.login(
                    usernameController.text,
                    passwordController.text,
                  );
                  if (result != null) {
                    // Успех: перенаправьте на HomeScreen
                    context.go('/');  // Перенаправление на home
                  } else {
                    // Ошибка: покажите snackbar
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text(authModel.errorMessage ?? 'Ошибка входа')),
                    );
                  }
                },
                child: const Text("Войти"),
              ),
            ),

          const SizedBox(height: 12),
          const Text("Вы еще не зарегистрированы?"),
          GestureDetector(
            onTap: () {
              context.go('/registration');
            },
            child: const Text(
              "Регистрация",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
        ],
      ),
    );
  }

  Widget customField(String label, {bool obscure = false}) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12),
      child: TextField(
        controller: obscure ? passwordController : usernameController,  // Привязка контроллера
        obscureText: obscure,
        decoration: InputDecoration(
          hintText: label,
          filled: true,
          fillColor: Colors.white,
          contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
          enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(30),
            borderSide: const BorderSide(color: Colors.green, width: 2),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(30),
            borderSide: const BorderSide(color: Colors.green, width: 2),
          ),
        ),
      ),
    );
  }
}