import 'package:flutter/material.dart';
import 'package:hmsweb/base/BaseScreen.dart';

import 'DoctorListScreenModel.dart';

class DoctorListScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return DoctorListScreenState();
  }

}

class DoctorListScreenState extends BaseScreen<DoctorListScreen, DoctorListScreenModel> {
  @override
  Widget buildBody(BuildContext context, DoctorListScreenModel viewModel) {
    return Material(
      child: Center(
        child: Builder(builder: (context) {
          if (viewModel.isLoading) {
            return CircularProgressIndicator();
          } else {
            return Expanded(
                child: SizedBox(
                    width: 200,
                    child: ListView.builder(
                        itemCount: viewModel.doctors.length,
                        itemBuilder: (context, index) {
                          final doctor = viewModel.doctors[index];
                          return Card(
                            child: ListTile(
                              title: Text(doctor.doctorName),
                              subtitle: Text(doctor.specialization),
                            ),
                          );
                        }
                    )
                )
            );
          }
        })
      ),
    );
  }

}