**Домашнее задание**

Ввести авторизацию на основе URL и/или доменных сущностей

**Цель**:      Научиться защищать приложение с помощью полноценной авторизации и разграничением прав доступа

**Результат**: Полноценное приложение с безопасностью на основе Spring Security

Внимание! Задание выполняется на основе нереактивного приложения Sping MVC!

Требования:
1. Минимум: настроить в приложении авторизацию на уровне URL.
2. Максимум: настроить в приложении авторизацию на основе доменных сущностей и методов сервиса.

Рекомендации по выполнению:
1. Не рекомендуется выделять пользователей с разными правами в разные классы - т.е. просто один класс пользователя.
2. В случае авторизации на основе доменных сущностей и PostgreSQL не используйте GUID для сущностей.
3. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены.

По умолчанию добавлены:

1. Пользователь name:user, password:user, role:USER 
2. Администратор name:admin, password:admin, role:ADMIN

ADMIN имеет доступ ко всем страницам, может изменять и добавлять сущности. 
USER видит книги, авторов и жанры,но не может ничего изменять и добавлять кроме комментариев к книге. 