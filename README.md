# CatChat
<img width="960" alt="30 03 2022 15_39_08" src="https://user-images.githubusercontent.com/73115406/160838581-fccf81be-a0bc-4ca1-ae0a-3ed84d7042c8.png">

## Описание 
Основной проект, в рамках которого, я отрабатываю полученные навыки и знания. Приложение выступает аналогом известных социальных сетей, по типу телеграм, но отличается отсутствием различных каналов(чатов), при этом сохраняя остальной функционал. Пользователь также заводит аккаунт, в который вносит информацию о себе, а после чего начинает общение со всеми участниками чата. Помимо этого, у него есть возможность просмотреть профили всех зарегистрированных пользователей.
## Модули
![Снимок экрана (2227)](https://user-images.githubusercontent.com/73115406/154504629-3acc65df-4b0c-4fe8-8c50-9953a3637a08.png)
Всего в приложении 8 экранов
* Стартовый экран.
* Экран входа
* Экран регистрации
* Экран восстановления пароля
* Главный экран чата
* Экран профиля
* Экран списка пользователей
* Экран профиля пользователя
## Что реализовано
* **SingleActivity** архитектура
* Паттерн **MVVM**
* Регистрация/вход пользователей с помощью **Firebase Authentication**
* Востановления пароля по email
* Профиль пользователя 
   * Фотография
   * Имя 
   * Почта 
   * Возможность смены пароля 
   * Редактирование информации 
   * Выход их аккаунта
* Экран чата с сообщениями (Имя отправителя, текст и дата отправки)
* Список пользователей (отображается статус активности)
* Профили пользователей
## Что планируется реализовать
* **Clean Architecture**
* Переход на **Kotlin**
* Большее количество чатов
* Локальное кеширование сообщений для нормальной работы уведомлений
* Добавить в профиль пользователя пункт "О себе"
* Раздел  "О приложении"
* _Голосовые сообщения_
* Добавить экран "О питомце"
* Отправка изображений
* Переход на **RxJava2** _50.50_
* _раздел с постами_
## Стек
- **Java** + **Kotlin**
- Нативные инструменты **Android SDK**
- Паттерн **MVVM + LiveData**
- **Google Firebase**
- **Glide** и **CropImage** для работы с изображениями
- **Android Navigation Component**
