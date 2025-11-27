import 'package:flutter/material.dart';
import 'package:flutter_web_plugins/url_strategy.dart';

import 'navigation/Navigation.dart' show router;

void main() {
  usePathUrlStrategy();
  runApp(const MyApp());

  //print(getCurrentWeek());
  print(getDateWithHour("2025-11-24T15:02:44"));

}

DateTime getDateWithHour(String isoString) {
  final dt = DateTime.parse(isoString);

  return DateTime(
    dt.year,
    dt.month,
    dt.day,
    dt.hour, // сохраняем только час
  );
}



List<Map<String, dynamic>> getCurrentWeek() {
  final today = DateTime.now();

  // Смещение к понедельнику (weekday: пн=1, вс=7)
  final monday = DateTime(today.year, today.month, today.day)
      .subtract(Duration(days: today.weekday - 1));

  return List.generate(7, (index) {
    final day = monday.add(Duration(days: index));

    final dateOnly = DateTime(day.year, day.month, day.day);

    return {
      "date": day.day,
      "weekday": day.weekday,
      "weekdayName": _weekdayName(day.weekday),
      "fullDate": dateOnly, // Теперь без времени
    };
  });
}

String _weekdayName(int weekday) {
  const names = {
    1: "Понедельник",
    2: "Вторник",
    3: "Среда",
    4: "Четверг",
    5: "Пятница",
    6: "Суббота",
    7: "Воскресенье",
  };
  return names[weekday]!;
}






class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      routerConfig: router,
    );
  }
}