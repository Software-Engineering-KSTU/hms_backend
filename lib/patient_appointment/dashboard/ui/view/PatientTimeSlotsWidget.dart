import 'package:flutter/material.dart';

import 'StatusRegistration.dart';

class TimeSlotsWidget extends StatelessWidget {

  final Map<String, StatusRegistration> status;

  TimeSlotsWidget({
    super.key,
    required this.status,
  });

  final List<String> times = [
    "08:00","08:20","08:40","09:00",
    "09:20","09:40","10:00","10:20",
    "10:40","11:00","11:20","11:40",
    "12:00","12:20","12:40","13:00",
    "13:20","13:40",
  ];

  Color getColor(String time) {
    switch (status[time]) {
      case StatusRegistration.mine: return Colors.blue;
      case StatusRegistration.busy: return Colors.red;
      default: return Colors.green;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [

        // Заголовок
        Text(
          "Выберите время",
          style: TextStyle(
            fontSize: 28,
            fontWeight: FontWeight.bold,
            color: Colors.blue,
          ),
        ),
        SizedBox(height: 16),

        // Легенда
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            legendDot(Colors.red, "Занято"),
            SizedBox(width: 20),
            legendDot(Colors.green, "Свободно"),
            SizedBox(width: 20),
            legendDot(Colors.blue, "Ваша запись"),
          ],
        ),

        SizedBox(height: 20),

        // Сетка кнопок
        Wrap(
          spacing: 16,
          runSpacing: 16,
          children: times.map((time) {
            return Container(
              padding: EdgeInsets.symmetric(horizontal: 22, vertical: 14),
              decoration: BoxDecoration(
                color: getColor(time),
                borderRadius: BorderRadius.circular(40),
              ),
              child: Text(
                time,
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 18,
                ),
              ),
            );
          }).toList(),
        ),
      ],
    );
  }

  Widget legendDot(Color color, String text) {
    return Row(
      children: [
        Container(
          width: 14,
          height: 14,
          decoration: BoxDecoration(
            color: color,
            shape: BoxShape.circle,
          ),
        ),
        SizedBox(width: 6),
        Text(
          text,
          style: TextStyle(color: Colors.black, fontSize: 16),
        ),
      ],
    );
  }
}