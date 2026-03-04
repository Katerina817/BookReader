Цель проекта: Веб-приложение «Читательский дневник», предназначенное для ведения личного учёта прочитанных книг, фиксации статуса чтения и создания заметок.

Потенциальные пользователи: Частные пользователи, ведущие личный читательский дневник.

Архитектура: монолит

Бэкенд: Spring Boot

Взаимодействие: REST API

База данных: PostgreSQL

Функциональные требования: 

<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/cf0c7714-a255-4417-97ba-d2cb9e9eaa15" />
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/c11ce6db-ea7c-4cc3-b755-cd7cf2639ba9" />
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/aed0e418-caad-4b49-aa37-a7424493a61b" />

Физическая модель базы данных:

<img width="824" height="515" alt="image" src="https://github.com/user-attachments/assets/c136a1b9-2840-47c8-abe4-62d22ac0ecd6" />

Rest API:

<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/b3ff8e2d-3d80-48a9-ab3d-9de564fde637" />
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/889d3552-e487-4265-8009-13d48efabfed" />
<img width="500" height="500" alt="image" src="https://github.com/user-attachments/assets/8fbe9b44-1513-4479-bfbb-30a5579451b1" />

Ресурсы:

- users (в текущей версии системы пользователь не имеет возможности редактировать данные учетной записи, поэтому отдельный REST-ресурс users не реализуется)

+ books

+ auth

+ readings

+ notes

+ genres

+ friends

В рамках проекта используется REST API с единым префиксом /api. Для операций аутентификации и регистрации выделен отдельный ресурс auth, тк данные операции включают бизнес-логику проверки учетных данных и управления сессией. В проекте используется версионирование REST API по URI (/api/v1/…) для обеспечения обратной совместимости. 

