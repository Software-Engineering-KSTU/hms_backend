import 'package:flutter/material.dart';
import 'package:hmsweb/base/BaseScreen.dart';
import 'package:hmsweb/doctor_appointment/dashboard/ui/view/TimeSlotsWidget.dart';
import 'package:table_calendar/table_calendar.dart';

import 'DoctorDashboardScreenModel.dart';

class DoctorDashboardScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return DoctorDashboardScreenState();
  }
}

class DoctorDashboardScreenState
    extends BaseScreen<DoctorDashboardScreen, DoctorDashboardScreenModel> {
  @override
  Widget buildBody(BuildContext context, DoctorDashboardScreenModel viewModel) {
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
            PatientSlotsWidget(status: viewModel.status, onTimeSelected: (String time) {  }),

          MaterialButton(onPressed: () {
            viewModel.setMineStatusRegistration();
          })
        ],
      ),
    );
  }
}
