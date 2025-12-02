import 'package:flutter/material.dart';

Widget BookTimeDialog({
  required String title,
  required String content,
  required BuildContext context,
  required String acceptText,
  required bool isShowNegative,
  Function()? onAccept,
}) {
  return AlertDialog(
    title: Text(title),
    content: Text(content),
    actions: [
      Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          if (isShowNegative)
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: Text("Отмена"),
            ),

          TextButton(
            onPressed: () {
              onAccept?.call();
              Navigator.pop(context);
            },
            child: Text(acceptText),
          ),
        ],
      ),
    ],
  );
}
