Руководство по созданию и настройке Django-проекта с PostgreSQL и .env
1. Введение

Django — это фреймворк на языке Python, предназначенный для создания веб-приложений.
Он включает готовые инструменты для работы с моделями, шаблонами, URL-маршрутами и административной панелью.

Этот документ описывает, как создать проект на Django, подключить базу данных PostgreSQL, использовать файл .env для хранения конфиденциальных данных и подготовить проект к совместной работе нескольких разработчиков.

2. PostgreSQL

PostgreSQL — это реляционная система управления базами данных. Django может использовать её в качестве основного хранилища данных через встроенный драйвер psycopg2-binary.

Для работы проекта необходимо:

установить PostgreSQL;
создать базу данных (например, mydatabase);
знать логин, пароль, хост и порт (обычно localhost:5432).

3. Файл .env

Файл .env используется для хранения конфиденциальных данных, которые не должны попадать в репозиторий.
В него выносятся такие параметры, как:

DEBUG=True
SECRET_KEY=замени_на_уникальный_ключ
DB_NAME=mydatabase
DB_USER=postgres
DB_PASSWORD=пароль
DB_HOST=localhost
DB_PORT=5432


Django подключается к этому файлу через библиотеку python-dotenv.

4. Зависимости проекта

Для работы проекта используются следующие библиотеки:

Django — фреймворк для веб-приложений
psycopg2-binary — драйвер для работы с PostgreSQL
python-dotenv — библиотека для загрузки переменных из .env

Все зависимости фиксируются в файле requirements.txt, чтобы проект можно было воспроизвести на другой машине.

5. Виртуальное окружение

Виртуальное окружение (venv) позволяет изолировать зависимости проекта от системных библиотек Python.
Оно создаётся один раз для каждого проекта и активируется перед запуском или установкой пакетов.

Команды для создания и активации окружения:

Windows
python -m venv venv
venv\Scripts\activate

Linux / macOS
python3 -m venv venv
source venv/bin/activate


После активации в начале командной строки появится префикс (venv).

6. Клонирование проекта

Чтобы развернуть проект, необходимо склонировать репозиторий из Git:

git clone https://github.com/username/projectname.git
cd projectname


Адрес репозитория меняется в зависимости от реального проекта.

7. Настройка проекта после клонирования

После того как проект загружен на компьютер, необходимо выполнить несколько шагов для его запуска.

7.1 Создать и активировать виртуальное окружение
Windows
python -m venv venv
venv\Scripts\activate

Linux / macOS
python3 -m venv venv
source venv/bin/activate

7.2 Установить зависимости
Windows
pip install -r requirements.txt

Linux / macOS
pip3 install -r requirements.txt

7.3 Создать файл .env

В корне проекта необходимо создать файл .env.
Если в репозитории есть пример (.env.example), можно скопировать его:

cp .env.example .env


В этом файле указываются ваши параметры базы данных и ключи:

DEBUG=True
SECRET_KEY=новый_секретный_ключ
DB_NAME=имя_вашей_базы
DB_USER=пользователь_postgres
DB_PASSWORD=пароль_postgres
DB_HOST=localhost
DB_PORT=5432


Если у вас другая база данных или пароль — просто укажите свои значения.

7.4 Настроить подключение к базе данных

В файле core/settings.py должно быть подключение к .env и PostgreSQL:

import os
from dotenv import load_dotenv

load_dotenv()

SECRET_KEY = os.getenv("SECRET_KEY")
DEBUG = os.getenv("DEBUG") == "True"

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': os.getenv("DB_NAME"),
        'USER': os.getenv("DB_USER"),
        'PASSWORD': os.getenv("DB_PASSWORD"),
        'HOST': os.getenv("DB_HOST"),
        'PORT': os.getenv("DB_PORT"),
    }
}

7.5 Применить миграции

Миграции создают таблицы в вашей базе данных.

Windows
python manage.py makemigrations
python manage.py migrate

Linux / macOS
python3 manage.py makemigrations
python3 manage.py migrate

7.6 Создать суперпользователя (для доступа в /admin)
Windows
python manage.py createsuperuser

Linux / macOS
python3 manage.py createsuperuser

7.7 Запустить сервер разработки
Windows
python manage.py runserver

Linux / macOS
python3 manage.py runserver


После запуска проект будет доступен по адресу:

http://127.0.0.1:8000/

8. Обновление зависимостей

Если в проекте были добавлены новые библиотеки, их можно зафиксировать в файле requirements.txt:

pip freeze > requirements.txt

9. .gitignore

Чтобы не добавлять в репозиторий служебные файлы, создайте файл .gitignore со следующим содержимым:

venv/
__pycache__/
*.pyc
*.sqlite3
.env
