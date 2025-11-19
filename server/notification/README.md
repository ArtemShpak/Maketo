# Модуль Сповіщень (Notification)

Модуль сповіщень для системи Maketo - надсилання email та внутрішніх повідомлень.

## Опис

Мікросервіс для роботи зі сповіщеннями. Підтримує email розсилки та внутрішні повідомлення застосунку через RabbitMQ.

## Структура модуля

```
notification/
├── notification-api/          # REST контролери
├── notification-core/         # Бізнес-логіка
├── notification-spi/          # Інтерфейси та DTO
├── notification-spi-adapter/  # Реалізації адаптерів
└── notification-microservice/ # Головний мікросервіс (порт 8081)
```

---

## notification-api

REST API шар для взаємодії з системою сповіщень.

### NotificationController
`com.maketo.notification.controller.NotificationController`

**Ендпоінти:**
- `GET /notification/health` - перевірка працездатності сервісу

---

## notification-spi

Інтерфейси та моделі даних для передачі сповіщень.

### Інтерфейси

#### SendEmailSpi
`com.maketo.notification.spi.SendEmailSpi`

Надсилання email сповіщень.

**Метод:**
```java
void sendEmail(EmailNotificationDto notification)
```

#### SendInAppNotificationSpi
`com.maketo.notification.spi.SendInAppNotificationSpi`

Надсилання внутрішніх сповіщень застосунку.

**Метод:**
```java
void sendNotification(InAppNotificationDto notification)
```

#### SendActivationEmailSpi
`com.maketo.notification.spi.SendActivationEmailSpi`

Надсилання активаційних листів (для сумісності з auth-сервісом).

**Метод:**
```java
void sendEmail(UserDto userData)
```

### DTO

#### EmailNotificationDto
`com.maketo.notification.spi.dto.EmailNotificationDto`

**Поля:**
- `recipientEmail` - email отримувача
- `recipientName` - ім'я отримувача  
- `subject` - тема листа
- `templateName` - назва Thymeleaf шаблону
- `templateVariables` - змінні для шаблону

**Приклад:**
```java
EmailNotificationDto email = new EmailNotificationDto(
    "user@example.com",
    "Іван",
    "Ласкаво просимо!",
    "welcome-email",
    Map.of("userName", "Іван")
);
```

#### InAppNotificationDto
`com.maketo.notification.spi.dto.InAppNotificationDto`

**Поля:**
- `userId` - ID користувача
- `title` - заголовок
- `message` - текст повідомлення
- `type` - тип (INFO, SUCCESS, WARNING, ERROR)
- `isRead` - статус прочитання
- `createdAt` - час створення

**Приклад:**
```java
InAppNotificationDto notification = new InAppNotificationDto(
    123L,
    "Замовлення створено",
    "Ваше замовлення успішно створено",
    NotificationType.SUCCESS
);
```

#### UserDto
`com.maketo.notification.spi.dto.UserDto`

Для надсилання активаційних листів (legacy).

**Поля:**
- `name` - ім'я користувача
- `email` - email
- `activationToken` - токен активації

---

## notification-core

Бізнес-логіка побудови та надсилання сповіщень.

### Будівельники (Builders)

#### EmailBuilder
`com.maketo.notification.core.builder.EmailBuilder`

Будівельник для створення та надсилання email (патерн Builder).

**Методи:**
```java
EmailBuilder to(String email)      // Встановити отримувача
EmailBuilder subject(String subject) // Вст��новити тему
EmailBuilder body(String htmlBody)  // Встановити HTML контент
void sendEmail()                    // Надіслати лист
```

**Приклад:**
```java
emailBuilder
    .to("user@example.com")
    .subject("Привіт!")
    .body("<h1>Ласкаво просимо</h1>")
    .sendEmail();
```

**Scope:** `prototype` - для кожного листа створюється новий екземпляр

#### EmailTemplateBuilder
`com.maketo.notification.core.builder.EmailTemplateBuilder`

Обробка Thymeleaf шаблонів для email.

**Ме��од:**
```java
String buildEmailTemplate(String templateName, Map<String, Object> variables)
```

**Приклад:**
```java
String html = emailTemplateBuilder.buildEmailTemplate(
    "verify-email",
    Map.of("name", "Іван", "token", "abc123")
);
```

### Конфігурація

#### RabbitMQConfig
`com.maketo.notification.core.config.RabbitMQConfig`

Конфігурація черг RabbitMQ.

**Exchange:** `maketo.exchange` (TopicExchange)

**Черги:**
- `notification.email.queue` → routing key: `notification.email`
- `notification.inapp.queue` → routing key: `notification.inapp`
- `user.registration.queue` → routing key: `user.registration`

**Конвертер:** Jackson2JsonMessageConverter (автоматична серіалізація JSON)

#### JavaMailSenderConfig
`com.maketo.notification.core.config.JavaMailSenderConfig`

Конфігурація SMTP для надсилання email.

**Налаштування з `email.properties`:**
```properties
email.host=smtp.gmail.com
email.port=587
email.username=your-email@gmail.com
email.password=your-app-password
```

### Переліки (Enums)

#### EmailType
`com.maketo.notification.core.enums.EmailType`

**Типи:**
- `VERIFY` - підтвердження email (шаблон: verify-email)
- `RESET_PASSWORD` - скидання пароля (шаблон: reset-password)
- `WELCOME` - вітальний лист (шаблон: welcome)

---

## notification-spi-adapter

Реалізації адаптерів для різних каналів.

### Email Адаптери

#### EmailNotificationAdapter
`com.maketo.notification.adapter.email.EmailNotificationAdapter`

Реалізує: `SendEmailSpi`

Надсилання email через EmailBuilder та шаблони.

**Логіка:**
1. Будує HTML з шаблону
2. Надсилає email через EmailBuilder

#### SendActivationEmailAdapter
`com.maketo.notification.adapter.email.SendActivationEmailAdapter`

Реалізує: `SendActivationEmailSpi`

Адаптер для активаційних листів (забезпечує сумісність з auth-сервісом).

**Логіка:**
1. Приймає UserDto
2. Перетворює в EmailNotificationDto
3. Делегує надсилання EmailNotificationAdapter

### In-App Адаптери

#### InAppNotificationAdapter
`com.maketo.notification.adapter.inapp.InAppNotificationAdapter`

Реалізує: `SendInAppNotificationSpi`

Обробка внутрішніх повідомлень.

**Логіка:**
1. Встановлює метадані (час, статус)
2. Логує повідомлення
3. TODO: збереження в БД, надсилання через WebSocket

---

## notification-microservice

Головний мікросервіс - точка входу застосунку.

### NotificationApplication
`com.maketo.notification.NotificationApplication`

Spring Boot застосунок (main клас).

**Порт:** 8081

### RabbitMQ Слухачі

#### EmailNotificationListener
`com.maketo.notification.listener.EmailNotificationListener`

Слухає: `notification.email.queue`

Обробляє EmailNotificationDto та викликає SendEmailSpi.

#### InAppNotificationListener
`com.maketo.notification.listener.InAppNotificationListener`

Слухає: `notification.inapp.queue`

Обробляє InAppNotificationDto та викликає SendInAppNotificationSpi.

#### UserRegistrationListener
`com.maketo.notification.listener.UserRegistrationListener`

Слухає: `user.registration.queue`

Обробляє UserDto від auth-сервісу та надсилає активаційний лист.

---

## Швидкий старт

### 1. Запустити RabbitMQ

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=admin \
  -e RABBITMQ_DEFAULT_PASS=admin \
  rabbitmq:3-management
```

### 2. Налаштувати email.properties

`notification-core/src/main/resources/email.properties`:
```properties
email.host=smtp.gmail.com
email.port=587
email.username=your-email@gmail.com
email.password=your-app-password
```

### 3. Зібрати та ��апустити

```bash
mvn clean install
cd notification-microservice
mvn spring-boot:run
```

### 4. Перевірити

```bash
curl http://localhost:8081/notification/health
```

---

## Використання з інших сервісів

### 1. Додати залежність

```xml
<dependency>
    <groupId>com.maketo</groupId>
    <artifactId>notification-spi</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Надіслати email

```java
EmailNotificationDto email = new EmailNotificationDto(
    "user@example.com",
    "Іван",
    "Тема листа",
    "template-name",
    Map.of("key", "value")
);

rabbitTemplate.convertAndSend("maketo.exchange", "notification.email", email);
```

### 3. Надіслати in-app повідомлення

```java
InAppNotificationDto notification = new InAppNotificationDto(
    userId,
    "Заголовок",
    "Повідомлення",
    NotificationType.INFO
);

rabbitTemplate.convertAndSend("maketo.exchange", "notification.inapp", notification);
```

---

## Конфігурація

### application.properties

```properties
server.port=8081
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

---

## Вирішення проблем

**Email не надсилаються:**
- Перевірте email.properties
- Для Gmail використовуйте "пароль застосунку"
- Перевірте логи на SMTP помилки

**RabbitMQ не підключається:**
- Перевірте що RabbitMQ запущено: `docker ps`
- Відкрийте http://localhost:15672 (admin/admin)

**Повідомлення не обробляються:**
- Перевірте черги в RabbitMQ UI
- Перевірте лог�� мікросервісу
- Переконайтеся що routing key правильний

