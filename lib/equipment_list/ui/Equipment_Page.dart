import 'package:flutter/material.dart';

class EquipmentPage extends StatefulWidget {
  const EquipmentPage({super.key});

  @override
  State<EquipmentPage> createState() => _EquipmentPageState();
}

class _EquipmentPageState extends State<EquipmentPage> {
  String selectedDepartment = "Хирургическое отделение";
  bool isGrid = false;

  final List<String> departments = [
    "Хирургическое отделение",
    "Терапевтическое отделение",
    "Педиатрия",
    "Кардиология",
    "Неврология",
  ];

  final List<Map<String, dynamic>> equipment = [
    {
      "icon": Icons.monitor_heart, // кардиомонитор
      "name": "Кардиомонитор",
      "count": 4,
      "livecount": 2,
      "status": "Исправен"
    },
    {
      "icon": Icons.wifi_tethering, // узи
      "name": "Аппарат УЗИ",
      "count": 2,
      "livecount": 2,
      "status": "Исправен"
    },
    {
      "icon": Icons.local_hospital, // дефибриллятор
      "name": "Дефибриллятор",
      "count": 1,
      "livecount": 0,
      "status": "Неисправен"
    },
    {
      "icon": Icons.camera, // эндоскоп
      "name": "Эндоскоп",
      "count": 3,
      "livecount": 3,
      "status": "Исправен"
    },
  ];

  Color getStatusColor(String status) {
    switch (status) {
      case "Исправен":
        return Colors.green;
      case "Неисправен":
        return Colors.red;
      default:
        return Colors.grey;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: const Text(
          "Оборудование отделения",
          style: TextStyle(color: Colors.black),
        ),
        elevation: 0,
        actions: [
          IconButton(
            icon: Icon(
              isGrid ? Icons.list : Icons.grid_view,
              color: Colors.blue.shade600,
              size: 30,
            ),
            onPressed: () => setState(() => isGrid = !isGrid),
          ),
        ],
      ),

      body: Padding(
        padding: const EdgeInsets.all(16.0),

        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            DropdownButtonFormField(
              value: selectedDepartment,
              decoration: InputDecoration(
                labelText: "Выберите отделение",
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
              ),
              items: departments.map((d) {
                return DropdownMenuItem(value: d, child: Text(d));
              }).toList(),
              onChanged: (value) {
                setState(() {
                  selectedDepartment = value!;
                });
              },
            ),

            const SizedBox(height: 20),

            Expanded(
              child: isGrid ? _buildGridView() : _buildListView(),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildListView() {
    return ListView.separated(
      itemCount: equipment.length,
      separatorBuilder: (_, __) => const SizedBox(height: 14),
      itemBuilder: (context, index) {
        final item = equipment[index];

        return Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(14),
            boxShadow: [
              BoxShadow(
                blurRadius: 8,
                color: Colors.black.withOpacity(0.05),
                offset: const Offset(0, 3),
              )
            ],
          ),

          child: Row(
            children: [
              Icon(item["icon"], size: 40, color: Colors.blue.shade600),

              const SizedBox(width: 16),

              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      item["name"],
                      style: const TextStyle(
                          fontSize: 18, fontWeight: FontWeight.bold),
                    ),
                    Text(
                      "Количество: ${item["count"]}",
                      style: const TextStyle(fontSize: 14, color: Colors.black54),
                    ),
                    Text("Доступно сейчас: ${item["livecount"]}",
                      style: const TextStyle(fontSize: 14, color: Colors.black54),)
                  ],
                ),
              ),

              Container(
                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                decoration: BoxDecoration(
                  color: getStatusColor(item["status"]).withOpacity(0.1),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(
                  item["status"],
                  style: TextStyle(
                    color: getStatusColor(item["status"]),
                    fontWeight: FontWeight.bold,
                    fontSize: 13,
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildGridView() {
    return GridView.builder(
      itemCount: equipment.length,
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 4,
        mainAxisSpacing: 16,
        crossAxisSpacing: 16,
        childAspectRatio: 1.2,
      ),
      itemBuilder: (context, index) {
        final item = equipment[index];

        return Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(14),
            boxShadow: [
              BoxShadow(
                blurRadius: 8,
                color: Colors.black.withOpacity(0.05),
                offset: const Offset(0, 3),
              )
            ],
          ),

          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(item["icon"], size: 48, color: Colors.blue.shade700),

              const SizedBox(height: 12),

              Text(
                item["name"],
                textAlign: TextAlign.center,
                style: const TextStyle(fontSize: 17, fontWeight: FontWeight.bold),
              ),

              const SizedBox(height: 6),

              Text(
                "Количество: ${item["count"]}",
                style: const TextStyle(fontSize: 14, color: Colors.black54),
              ),
              Text("Доступно сейчас: ${item["livecount"]}",
                style: const TextStyle(fontSize: 14, color: Colors.black54)),

              const SizedBox(height: 8),

              Container(
                padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                decoration: BoxDecoration(
                  color: getStatusColor(item["status"]).withOpacity(0.1),
                  borderRadius: BorderRadius.circular(10),
                ),
                child: Text(
                  item["status"],
                  style: TextStyle(
                    color: getStatusColor(item["status"]),
                    fontWeight: FontWeight.bold,
                    fontSize: 12,
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
