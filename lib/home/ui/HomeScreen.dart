import 'package:flutter/material.dart';
import 'package:hmsweb/base/BaseScreen.dart';
import 'package:hmsweb/base/view/CustomFooter.dart';
import 'package:hmsweb/home/ui/view/WhyUsSection.dart';

import 'HomeModel.dart';

class HomeScreen extends StatefulWidget {

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends BaseScreen<HomeScreen, HomeModel> {

  @override
  Widget buildBody(BuildContext context, HomeModel viewModel) {
    return Column(
      children: [
        SizedBox(
          width: double.infinity,
          height: 600,

          child: Stack(
            alignment: Alignment.centerLeft,
            children: [
              Image.asset(
                'assets/images/med_clinic_main.jpg',
                fit: BoxFit.cover,
                height: double.infinity,
                width: double.infinity,
              ),

              Container(color: Colors.black54),

              Positioned(
                left: 150,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Центр медецинснской \nпомощи',
                      style: TextStyle(fontSize: 30, color: Colors.white),
                    ),

                    SizedBox(height: 25),

                    Text(
                      'Ваше здоровье - наша забота !',
                      style: TextStyle(fontSize: 15, color: Colors.white),
                    ),

                    SizedBox(height: 15),

                    ElevatedButton(
                      onPressed: () {},
                      child: Text(
                        "Запистаься на прием",
                        style: TextStyle(color: Colors.blue, fontSize: 15),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),

        Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            children: [
              const Text('Наши услуги', style: TextStyle(fontSize: 18)),

              GridView.builder(
                shrinkWrap: true,
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 3, // количество колонок
                  mainAxisSpacing: 16,
                  crossAxisSpacing: 16,
                  childAspectRatio: 6,
                ),
                itemCount: viewModel.departments.length,
                itemBuilder: (context, index) {
                  final department = viewModel.departments[index];
                  return Card(child: Center(child: Text(department.name)));
                },
              ),
            ],
          ),
        ),

        Padding(
          padding: EdgeInsets.all(10),
          child: Container(
            width: double.infinity,
            color: Color(0x101C9AEA),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(20.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text(
                          "Добро пожаловать!",
                          style: TextStyle(fontSize: 30, color: Colors
                              .blueAccent),
                        ),
                        Text(
                            '''Здоровье - это самое важное и ценное, что у нас есть. Это основа для достижения всех наших мечт и целей в жизни. Не откладывайте заботу о себе и свих близких на потом. Сегодня - лучшее время начать заботиться о себе и своей семье.

В нашей медицинской клинике мы готовы помочь вам на этом пути к благополучию и процветанию. Наши врачи и медицинский персонал обладают высокой квалификацией и опытом, мы предоставляем широкий спектр медицинских услуг для удовлетворения ваших потребностей.

Не ждите, пока заболеете или проблемы ухудшатся. Запишитесь на прием уже сегодня, позвольте себе заботиться о самом важном - о себе и своих родных.

Сделайте шаг к более здоровой, счастливой и активной жизни. Вместе мы сделаем это возможным! Запишитесь на прием прямо сейчас и уделите себе заслуженное внимание и заботу!''',
                            style: TextStyle(fontSize: 15)
                        ),

                        SizedBox(height: 25),

                        ElevatedButton(
                          onPressed: () {},
                          child: Text(
                            "Записаться на прием",
                            style: TextStyle(
                              color: Colors.blue,
                              fontSize: 15,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),

                // Картинка (фиксированная ширина)
                Image.asset(
                  'assets/images/doctor.jpg',
                  height: 600, // Задайте фиксированную высоту!
                  width: 400,
                  fit: BoxFit.cover,
                ),
              ],
            ),
          ),
        ),

        WhyUsSection(),

        WebPageWithFooter(),
      ],
    );
  }
}
