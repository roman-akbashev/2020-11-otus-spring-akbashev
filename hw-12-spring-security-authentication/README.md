**Домашнее задание**

В CRUD Web-приложение добавить механизм аутентификации

**Цель**:      Защитить Web-приложение аутентифкацией и простой авторизацией

**Результат**: Приложение с использованием Spring Security

Внимание! Задание выполняется на основе нереактивного приложения Sping MVC!

Требования:
1. Добавить в приложение новую сущность - пользователь. Не обязательно реализовывать методы по созданию пользователей - допустимо добавить пользователей только через БД-скрипты.
2. В существующее CRUD-приложение добавить механизм Form-based аутентификации.
3. UsersServices реализовать самостоятельно.
4. Авторизация на всех страницах - для всех аутентифицированных. Форма логина - доступна для всех.
5. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены.