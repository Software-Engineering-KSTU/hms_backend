import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart' show Scaffold, AppBar, Colors;

class WebPageWithFooter extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          width: double.infinity,
          padding: const EdgeInsets.symmetric(vertical: 40, horizontal: 20),
          color: Colors.blue.shade50,
          child: const Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Наши контакты:',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              Text('Email: info@example.com'),
              SizedBox(height: 10),
              Text('© 2025 Все права защищены.'),
            ],
          ),
        ),
      ],
    );
  }
}
