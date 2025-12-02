import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';

class Error500Page extends StatelessWidget {
  const Error500Page({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF7FAFC),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(32.0),
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 1100),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  flex: 1,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [

                      AutoSizeText(
                        "500",
                        maxLines: 1,
                        minFontSize:60,
                        style: TextStyle(
                          fontSize: 160,
                          fontWeight: FontWeight.bold,
                          color: Colors.red.shade600,
                        ),
                      ),

                      const SizedBox(height: 16),

                      const AutoSizeText(
                        "Внутренняя ошибка сервера",
                        maxLines: 1,
                        minFontSize: 18,
                        style: TextStyle(
                          fontSize: 36,
                          fontWeight: FontWeight.bold,
                        ),
                      ),

                      const SizedBox(height: 12),

                      const AutoSizeText(
                        "Произошла непредвиденная ошибка.\n"
                            "Наши специалисты уже работают над её устранением.",
                        minFontSize: 12,
                        style: TextStyle(
                          fontSize: 18,
                          color: Colors.black54,
                        ),
                      ),

                      const SizedBox(height: 32),

                      SizedBox(
                        width: 250,
                        child: ElevatedButton(
                          onPressed: () {},
                          style: ElevatedButton.styleFrom(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 24,
                              vertical: 18,
                            ),
                            backgroundColor: Colors.red.shade600,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(12),
                            ),
                          ),
                          child: const AutoSizeText(
                            "Вернуться на главную",
                            maxLines: 1,
                            minFontSize: 12,
                            style: TextStyle(
                              fontSize: 18,
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(width: 40),
                Expanded(
                  flex: 1,
                  child: Image.asset(
                    "assets/images/errorpage.png",
                    fit: BoxFit.contain,
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
