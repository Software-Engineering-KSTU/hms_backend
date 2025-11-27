import 'package:flutter/material.dart';
import 'package:hmsweb/base/BaseScreen.dart';
import 'package:hmsweb/doctor_appointment/dashboard/ui/view/TimeSlotsWidget.dart';
import 'package:table_calendar/table_calendar.dart';

import 'PatientDashboardScreenModel.dart';

class PatientDashboardScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return PatientDashboardScreenState();
  }
}

class PatientDashboardScreenState
    extends BaseScreen<PatientDashboardScreen, PatientDashboardScreenModel> {
  @override
  Widget buildBody(
    BuildContext context,
    PatientDashboardScreenModel viewModel,
  ) {
    return Material(
      child: Column(
        children: [
          Center(
            child: TableCalendar(
              firstDay: DateTime.utc(2025, 1, 1),
              lastDay: DateTime.utc(2030, 12, 31),
              focusedDay: viewModel.focusedDay,
              selectedDayPredicate: (day) =>
                  isSameDay(viewModel.selectedDay, day),
              onDaySelected: (selected, focused) {
                setState(() {
                  viewModel.selectedDay = selected;
                  viewModel.focusedDay = focused;
                });
              },
              headerStyle: HeaderStyle(
                titleCentered: true,
                formatButtonVisible: false,
              ),

              calendarStyle: CalendarStyle(
                todayDecoration: BoxDecoration(
                  color: Colors.blue.shade100,
                  shape: BoxShape.circle,
                ),

                selectedDecoration: BoxDecoration(
                  color: Colors.blue,
                  shape: BoxShape.circle,
                ),
                selectedTextStyle: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),

          if (viewModel.isLoading)
            CircularProgressIndicator()
          else
            TimeSlotsWidget(
              status: viewModel.status,
              onTimeSelected: (String time) {
                showDialog(
                  context: context,
                  builder: (context) {
                    return AlertDialog(
                      title: Text(time),
                      content: Text("Вы действительно хотите записаться на это время ?"),
                      actions: [
                        TextButton(
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          child: Text("Отмена"),
                        ),

                        Spacer(),

                        TextButton(
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          child: Text("Да"),
                        ),
                      ],
                    );
                  },
                );
              },
            ),
        ],
      ),
    );
  }
}
