import 'package:flutter/material.dart';

class FeatureCard extends StatelessWidget {
  final String title;
  final String description;

  const FeatureCard({
    required this.title,
    required this.description,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      // Задаем максимальную ширину для карточки в сетке
      width: 300,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Иконка "галочка" в желтом круге (используем Icon, имитируя стилизацию)
              const Icon(
                Icons.check_circle,
                color: Color(0xFF1C9AEA), // Желтый/Оранжевый цвет
                size: 24,
              ),
              const SizedBox(width: 10),
              // Заголовок преимущества
              Expanded(
                child: Text(
                  title,
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF1E2749), // Темно-синий цвет
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(height: 10),
          // Описание преимущества
          Text(
            description,
            style: const TextStyle(
              fontSize: 14,
              color: Colors.grey,
            ),
          ),
        ],
      ),
    );
  }
}