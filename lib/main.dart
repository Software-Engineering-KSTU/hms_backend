import 'package:flutter/material.dart';
import 'package:flutter_web_plugins/url_strategy.dart';

import 'navigation/Navigation.dart' show router;

void main() {
  usePathUrlStrategy();
  runApp(const MyApp());
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