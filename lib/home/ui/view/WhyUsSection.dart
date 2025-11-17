import 'package:flutter/material.dart';

import 'FeatureCard.dart';
class WhyUsSection extends StatelessWidget {
  const WhyUsSection({super.key});

  @override
  Widget build(BuildContext context) {
    // Используем LayoutBuilder для адаптивности:
    // на узких экранах (мобильный) контент будет stacked,
    // на широких (веб) — разделен на две колонки.
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 60.0, horizontal: 80.0),
      child: LayoutBuilder(
        builder: (context, constraints) {
          // Если ширина меньше 1000, используем Column (моб. вид)
          final bool isNarrow = constraints.maxWidth < 1000;

          // Контент преимуществ (4 карточки)
          final featuresContent = Column(
            children: [
              // Верхний ряд (две карточки)
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  FeatureCard(
                    title: 'Высококвалифицированный медицинский персонал',
                    description: 'В нашей клинике работают опытные врачи и медицинский персонал, готовые предоставить вам высококачественную медицинскую помощь. Ваше здоровье и благополучие – наш приоритет.',
                  ),
                  FeatureCard(
                    title: 'Современное оборудование и технологии',
                    description: 'Мы обеспечиваем вас доступом к современным медицинским технологиям и оборудованию, что позволяет нам быстро и точно диагностировать и лечить ваше заболевание. Ваши результаты и комфорт – наша забота.',
                  ),
                ],
              ),

              const SizedBox(height: 40), // Отступ между рядами

              // Нижний ряд (две карточки)
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  FeatureCard(
                    title: 'Подход к пациентам',
                    description: 'Мы ценим каждого пациента и гарантируем заботливое и внимательное обслуживание. Ваши вопросы, потребности и комфорт всегда наш важнейший приоритет.',
                  ),
                  FeatureCard(
                    title: 'Широкий спектр медицинских услуг',
                    description: 'В нашей клинике вы найдете разнообразные медицинские услуги, от общей консультации до специализированных процедур. Мы здесь, чтобы удовлетворить все ваши потребности в здоровье.',
                  ),
                ],
              ),
            ],
          );

          // Контент заголовка
          final titleContent = Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: const [
              Text(
                'Центр медецинской помощи',
                style: TextStyle(
                  fontSize: 12,
                  color: Colors.blue, // Зеленый цвет
                  letterSpacing: 1.5,
                ),
              ),
              SizedBox(height: 10),
              Text(
                'Почему мы?',
                style: TextStyle(
                  fontSize: 36,
                  fontWeight: FontWeight.bold,
                  color: Color(0xFF1E2749),
                ),
              ),
              SizedBox(height: 10),
              Text(
                'Основные плюсы нашей клиники:',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.grey,
                ),
              ),
            ],
          );

          // === АДАПТИВНЫЙ МАКЕТ ===
          if (isNarrow) {
            // Версия для узких экранов (заголовок сверху, контент снизу)
            return Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                titleContent,
                const SizedBox(height: 40),
                featuresContent,
              ],
            );
          } else {
            // Версия для широких экранов (две колонки)
            return Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Левая колонка для заголовка (фиксированная ширина)
                SizedBox(width: 300, child: titleContent),
                const SizedBox(width: 80),
                // Правая колонка для преимуществ (занимает оставшееся место)
                Expanded(child: featuresContent),
              ],
            );
          }
        },
      ),
    );
  }
}