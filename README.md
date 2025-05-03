# HarryPotter 🪄⚡🧹⚯

## Развёртывание базы данных

1. Установите PostgreSQL:
  Windows
a. Скачивание установщика
Перейдите на официальный сайт PostgreSQL(https://www.postgresql.org/download/).

Выберите версию для Windows и скачайте установщик.

b. Установка
Запустите скачанный .exe-файл.

Выберите компоненты (оставьте всё по умолчанию, включая pgAdmin — графический интерфейс).

Укажите директорию установки (например, C:\Program Files\PostgreSQL\16).

Пользователя назовите postgres. Задайте пароль для суперпользователя (pipipupu). Запомните его!

Порт оставьте 5432 (если не занят).

Нажмите Next → Next → Finish.

c. Проверка установки
Откройте pgAdmin из меню «Пуск».

Введите пароль, заданный при установке.

Создайте новую БД:

Правой кнопкой на Databases → Create → Database.

Имя: harrypotter.

Linux 
a. Установка из репозитория
Откройте терминал и выполните:
bash
sudo apt update
sudo apt install postgresql postgresql-contrib

b. Запуск сервиса
bash
sudo systemctl start postgresql
sudo systemctl enable postgresql 

c. Настройка пользователя и БД
Переключитесь на пользователя postgres:
bash
sudo -i -u postgres

Создайте БД и пользователя:
bash
createdb harrypotter
psql -c "CREATE USER postgres WITH PASSWORD pipipupu;"
psql -c "GRANT ALL PRIVILEGES ON DATABASE harrypotter TO postgres;"

d. Проверка
Подключитесь к БД:
bash
psql -h localhost -U postgres -d harrypotter
Если видите приглашение harrypotter=#, установка успешна.

5. Настройка Hibernate
Аналогично Windows (см. выше).


2. Создайте базу данных:
   ```
   createdb harrypotter
   ```
3. Импортируйте структуру и/или данные:
   ```
   psql -U <user> -d harrypotter -f db/harrypotter_dump.sql
   ```
   или
   ```
   psql -U <user> -d harrypotter -f db/schema.sql
   ```
