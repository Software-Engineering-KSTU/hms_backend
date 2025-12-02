import 'package:flutter/material.dart';

class BaseTemplate extends StatelessWidget {
  final Widget child;

  const BaseTemplate({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [

          /// Фон
          Container(
            height: double.infinity,
            width: double.infinity,
            decoration: const BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/images/doctor_bg.jpg"),
                fit: BoxFit.cover,
              ),
            ),
          ),

          /// Белая линия сверху + меню
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
              height: 55,
              color: Colors.white.withOpacity(0.95),
              child: Row(
                children: [
                  const SizedBox(width: 12),
                  const Text(
                    "HMS",
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  const Spacer(),
                  navButton("Главная"),
                  navButton("Отделение"),
                  navButton("Контакты"),
                  navButton("Запись к врачу"),
                  const SizedBox(width: 16),
                ],
              ),
            ),
          ),

          /// Основной контент (регистрация / авторизация)
          Center(child: child),
        ],
      ),
    );
  }

  /// Вид кнопки навигации
  Widget navButton(String text) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16),
      child: Text(
        text,
        style: const TextStyle(fontSize: 14),
      ),
    );
  }
}