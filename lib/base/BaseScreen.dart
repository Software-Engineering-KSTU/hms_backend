import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';

import 'BaseScreenModel.dart';

abstract class BaseScreen<S extends StatefulWidget, VM extends BaseScreenModel>
    extends State<S> {

  Widget buildBody(BuildContext context, VM viewModel);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Consumer<VM>(
        builder: (context, viewModel, child) {
          return buildBody(context, viewModel);
        },
      ),
    );
  }
}
