import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  const CustomAppBar({super.key});

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Row(
        mainAxisSize: MainAxisSize.min,
        children: [

          Title(color: Colors.black, child: Text("HMS")),

          Spacer(flex: 1),

          ToggleButtonsGroup(),

          Spacer(flex: 5),
        ],
      ),
      centerTitle: false,
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}

class ToggleButtonsGroup extends StatefulWidget {
  const ToggleButtonsGroup({super.key});

  @override
  State<ToggleButtonsGroup> createState() => _ToggleButtonsGroupState();
}

class _ToggleButtonsGroupState extends State<ToggleButtonsGroup> {

  int _selectedIndex = 0;
  final List<String> _buttonTitles = ['Главная', 'Отделения', 'Контакты', 'Запись к врачу'];

  void _selectButton(int index) {

    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: List.generate(_buttonTitles.length, (index) {
          final bool isActive = index == _selectedIndex;

          return Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: TextButton(
              onPressed: () => _selectButton(index),

              style: TextButton.styleFrom(
                backgroundColor: isActive
                    ? Colors.blue.shade100
                    : Colors.transparent,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8)),
              ),
              child: Text(
                _buttonTitles[index],
                style: TextStyle(
                  color: isActive ? Colors.blue.shade800 : Colors.black87,
                  fontWeight: isActive ? FontWeight.bold : FontWeight.normal,
                ),
              ),
            ),
          );
        }),
      ),
    );
  }
}
