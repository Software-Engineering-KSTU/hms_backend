import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DoctorSummary extends StatelessWidget {
  const DoctorSummary({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF7FAFC),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(32),
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 1100),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Expanded(
                  flex: 1,
                  child: Column(
                    children: [
                      ClipRRect(
                        borderRadius: BorderRadius.circular(20),
                        child: Image.asset(
                          "assets/images/doctor_resume.jpg",
                          fit: BoxFit.cover,
                        ),
                      ),
                      const SizedBox(height: 20),
                    ],
                  ),
                ),

                const SizedBox(width: 40),
                Expanded(
                  flex: 2,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      AutoSizeText(
                        "Айбеков Тимур Жанышбаевич",
                        maxLines: 1,
                        minFontSize: 22,
                        style: const TextStyle(
                          fontSize: 34,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      const SizedBox(height: 16),

                      Chip(
                        backgroundColor: Colors.blue.shade50,
                        label: Text(
                          "Хирург-травматолог",
                          style: TextStyle(
                            color: Colors.blue.shade600,
                            fontSize: 16,
                          ),
                        ),
                      ),


                      const SizedBox(height: 20),

                      Row(
                        children: [
                          Icon(Icons.badge, color: Colors.blue.shade600),
                          const SizedBox(width: 8),
                          const Text(
                            "Стаж: 12 лет",
                            style: TextStyle(
                              fontSize: 18,
                              color: Colors.black87,
                            ),
                          ),
                        ],
                      ),

                      const SizedBox(height: 14),

                      Row(
                        children: [
                          Icon(Icons.school, color: Colors.blue.shade600),
                          const SizedBox(width: 8),
                          const Expanded(
                            child: Text(
                              "Окончил Кыргызско-Российский Славянский университет по специальности «Лечебное дело».",
                              style: TextStyle(fontSize: 18),
                            ),
                          ),
                        ],
                      ),

                      const SizedBox(height: 30),

                      const Text(
                        "О враче",
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      const SizedBox(height: 10),

                      const Text(
                        "Доктор Айбеков является высококвалифицированным специалистом в области хирургии и травматологии. "
                            "Регулярно проходит международные курсы, владеет современными методами лечения, внимателен и доброжелателен.",
                        style: TextStyle(fontSize: 18, height: 1.5),
                      ),

                      const SizedBox(height: 40),

                      SizedBox(
                        width: 260,
                        child: ElevatedButton(
                          onPressed: () {},
                          style: ElevatedButton.styleFrom(
                            padding: const EdgeInsets.symmetric(
                              vertical: 18,
                            ),
                            backgroundColor: Colors.blue.shade600,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(14),
                            ),
                          ),
                          child: const Text(
                            "Записаться на приём",
                            style: TextStyle(fontSize: 18, color: Colors.white),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
