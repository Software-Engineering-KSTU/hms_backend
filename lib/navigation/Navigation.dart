import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/base/view/CustomAppBar.dart';
import 'package:hmsweb/home/ui/HomeModel.dart';
import 'package:hmsweb/home/ui/HomeScreen.dart';
import 'package:provider/provider.dart';

import '../doctor_appointment/dashboard/ui/DoctorDashboardScreen.dart';
import '../doctor_appointment/dashboard/ui/DoctorDashboardScreenModel.dart';
import '../patient_appointment/dashboard/ui/PatientDashboardScreen.dart';
import '../patient_appointment/dashboard/ui/PatientDashboardScreenModel.dart';

GoRoute buildRoute<T extends BaseScreenModel>({
  required String path,
  required Widget screen,
  required T Function() createModel,
}) {
  return GoRoute(
    path: path,
    builder: (context, state) {

      final model = createModel();
      model.initialize();

      return ChangeNotifierProvider.value(
        value: model,
        child: screen,
      );
    },
  );
}

final GoRouter router = GoRouter(
  routes: [
    buildRoute(
        path: '/doctor/dashboard',
        screen: DoctorDashboardScreen(),
        createModel: () => DoctorDashboardScreenModel()),

    buildRoute(
        path: '/patient/dashboard',
        screen: PatientDashboardScreen(),
        createModel: () => PatientDashboardScreenModel()),

    ShellRoute(
      builder: (context, state, child) {
        return Scaffold(
          appBar: const CustomAppBar(),
          body: child,
        );
      },
      routes: [
        buildRoute(
            path: '/',
            screen: HomeScreen(),
            createModel: () => HomeModel()
        ),

      ],
    ),
  ],
);

class TestScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.red,
    );
  }




}