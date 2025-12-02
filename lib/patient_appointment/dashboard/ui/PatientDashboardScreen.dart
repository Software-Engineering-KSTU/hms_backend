import 'package:flutter/material.dart';
import 'package:hmsweb/base/BaseScreen.dart';
import 'package:hmsweb/doctor_appointment/dashboard/ui/view/TimeSlotsWidget.dart';
import 'package:hmsweb/patient_appointment/dashboard/ui/view/BookTimeDialog.dart';
import 'package:table_calendar/table_calendar.dart';

import 'PatientDashboardScreenModel.dart';

class PatientDashboardScreen extends StatefulWidget {
  const PatientDashboardScreen({super.key});

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

          if (viewModel.isLoading) CircularProgressIndicator()
          else
            PatientSlotsWidget(
              status: viewModel.status,
              onTimeSelected: (String time) {
                showDialog(
                  context: context,
                  builder: (context) {
                    if (viewModel.isNotBusyOrMine(time)) {
                      return BookTimeDialog(
                        title: time,
                        content:
                            "Вы действительно хотите записаться на это время ?",
                        context: context,
                        onAccept: () {
                          viewModel.setMineStatusRegistration(time);
                        },
                        acceptText: 'Да',
                        isShowNegative: true,
                      );
                    } else {
                      return BookTimeDialog(
                        title: "Время занято",
                        content: "На это время уже есть запись",
                        context: context,
                        acceptText: 'Ок',
                        isShowNegative: false,
                      );
                    }
                  },
                );
              },
            ),
        ],
      ),
    );
  }
}
