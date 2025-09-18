# hms
Hospital Management System

Установка и запуск проекта:

Если у вас Windows:
Устанавливаем JDK как на видео:
https://www.youtube.com/watch?v=FXh0GN1qokY

Затем устанавливаем Maven как на видео:
https://www.youtube.com/watch?v=YTvlb6eny_0

Затем устанавливаем редактор кода Intellij Idea от JetBreains по следующей ссылке:
https://www.jetbrains.com/idea/download/?section=windows

Далее из командной строки вводим команду git clone https://github.com/em-kstu/hms
Это склонирует наш репозиторий вам на компьютер.

после соединяемся с базой данных https://www.youtube.com/watch?v=RAdduEhWMks

Затем в нашем Ide открыв наш проект мы запускаем его следующими командами, которые нам следует ввести из командной строки:
cd hospital_management_system
mvn clean install
mvn spring-boot:run


Если у вас MacOS
Установка JDK

Через Homebrew:

brew update
brew install openjdk@21


Добавляем в переменные окружения:

echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
source ~/.zshrc


Проверка:
java -version

Установка Maven
brew install maven

Проверка:

mvn -v

Установка IDE (IntelliJ IDEA)

Скачиваем с официального сайта:
https://www.jetbrains.com/idea/download/?section=mac

Клонирование репозитория
git clone https://github.com/em-kstu/hms
cd hms

Открываем проект из ide:
Соединение с базой данных
Следуем инструкции из видео:
https://www.youtube.com/watch?v=RAdduEhWMks

Сборка и запуск проекта
В терминале:

cd hospital_management_system
mvn clean install
mvn spring-boot:run


Если Ubuntu:

Установка JDK:
sudo apt update
sudo apt install openjdk-21-jdk -y

Проверяем установку:
java -version
javac -version

Устанавливаем Maven:
sudo apt install maven -y
Проверяем Maven:

bash
Copy code
mvn -v
Скачиваем и устанавливаем IntelliJ IDEA с официального сайта:
https://www.jetbrains.com/idea/download/?section=linux

Или через snap:

bash
Copy code
sudo snap install intellij-idea-community --classic
Клонируем репозиторий и переходим в него:

bash
Copy code
git clone https://github.com/em-kstu/hms
cd hms
Следуем инструкции по соединению с базой данных:
https://www.youtube.com/watch?v=RAdduEhWMks

Собираем и запускаем проект:

bash
Copy code
cd hospital_management_system
mvn clean install
mvn spring-boot:run
