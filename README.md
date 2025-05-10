# HarryPotter 🪄⚡🧹⚯

## Развёртывание базы данных

1. Установка SQLite:
   - Windows:
     - Скачайте SQLite с официального сайта: https://www.sqlite.org/download.html
     - Распакуйте архив и добавьте путь к sqlite3.exe в переменную PATH
   
   - macOS:
     ```bash
     brew install sqlite3
     ```
   
   - Linux:
     ```bash
     sudo apt-get update
     sudo apt-get install sqlite3
     ```

2. База данных будет создана автоматически при первом запуске приложения в файле `harrypotter.db` в корневой директории проекта.

3. Для просмотра и управления базой данных можно использовать:
   - DB Browser for SQLite (рекомендуется): https://sqlitebrowser.org/
   - SQLite Studio: https://sqlitestudio.pl/
   - Или командную строку sqlite3:
     ```bash
     sqlite3 harrypotter.db
     ```

## Запуск приложения

1. Убедитесь, что у вас установлена Java 22 или выше
2. Соберите проект с помощью Maven:
   ```bash
   mvn clean package
   ```
3. Запустите приложение:
   ```bash
   java -jar target/harrypotter-1.0-SNAPSHOT.jar
   ```

## Структура проекта

- `src/main/java/com/entity/` - классы сущностей
- `src/main/java/com/dao/` - классы для работы с базой данных
- `src/main/java/com/main/` - основной код приложения
- `src/main/resources/` - конфигурационные файлы
