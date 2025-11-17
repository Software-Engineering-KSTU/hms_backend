import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/base/view/CustomAppBar.dart';
import 'package:hmsweb/home/ui/HomeModel.dart';
import 'package:hmsweb/home/ui/HomeScreen.dart';
import 'package:provider/provider.dart';

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

        GoRoute(path: '/login', builder: (context, state) => TestScreen()),
        

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