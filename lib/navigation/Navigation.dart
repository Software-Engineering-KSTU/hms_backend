import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:hmsweb/base/BaseScreenModel.dart';
import 'package:hmsweb/base/view/CustomAppBar.dart';
import 'package:hmsweb/home/ui/HomeModel.dart';
import 'package:hmsweb/home/ui/HomeScreen.dart';
import 'package:provider/provider.dart';
import '../doctors_summary/ui/Doctor_summery.dart';
import '../errorpage/ui/Error404Page.dart';
import '../errorpage/ui/Error500Page.dart';
import 'package:hmsweb/auth/ui/LoginScreen.dart';  // Импорт LoginScreen
import 'package:hmsweb/auth/ui/RegistrationScreen.dart';  // Импорт RegistrationScreen
import 'package:hmsweb/auth/AuthModel.dart';  // Импорт AuthModel

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
  // initialLocation: '/registration',  // Начните с логина, если нужно
  routes: [
//
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
        buildRoute(
          path: '/login',
          screen: LoginScreen(),
          createModel: () => AuthModel(),
        ),
        buildRoute(
          path: '/registration',
          screen: RegistrationScreen(),
          createModel: () => AuthModel(),
        ),
      ],
    ),
  ],
);

// final GoRouter router = GoRouter(
//   routes: [
//     // Auth-роуты без ShellRoute, чтобы избежать конфликта панели
//     buildRoute(
//       path: '/login',
//       screen: LoginScreen(),
//       createModel: () => AuthModel(),
//     ),
//     buildRoute(
//       path: '/registration',
//       screen: RegistrationScreen(),
//       createModel: () => AuthModel(),
//     ),
//     ShellRoute(
//       builder: (context, state, child) {
//         return Scaffold(
//           appBar: const CustomAppBar(),
//           body: child,
//         );
//       },
//       routes: [
//         buildRoute(
//           path: '/',
//           screen: HomeScreen(),
//           createModel: () => HomeModel(),
//         ),
//       ],
//     ),
//   ],
// );
// class TestScreen extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return Container(
//       color: Colors.red,
//     );
//   }
//
//
//
//
// }