# Модуль Автентифікації (Auth)

Модуль автентифікації та авторизації для системи Maketo.

## Опис

Мікросервіс для управління користувачами, реєстрації, автентифікації та авторизації з використанням JWT токенів.

## Структура модуля

```
auth/
├── auth-api/          # REST контролери
├── auth-core/         # Бізнес-логіка
├── auth-spi/          # Інтерфейси
├── auth-spi-adapter/  # Реалізації адаптерів
└── auth-microservice/ # Головний мікросервіс (порт 8080)
```

---

## auth-api

REST API шар для автентифікації та управління користувачами.

### AuthController

Основний контролер для операцій з автентифікацією.

**Ендпоінти будуть додані в процесі розробки**

---

## auth-spi

Інтерфейси для роботи з автентифікацією та користувачами.

### Service Provider Interfaces

Визначають контракти для взаємодії з зовнішніми системами та бізнес-логікою.

---

## auth-core

Бізнес-логіка автентифікації та авторизації.

### Основні компоненти

#### Обробка JWT
- Генерація JWT токенів
- Валідація токенів
- Витягування даних з токенів

#### Безпека
- Хешування паролів (BCrypt)
- Управління сесіями
- Механізм Refresh token

### Конфігурація

#### Налаштування JWT

Налаштування з `jwt.properties`:
```properties
jwt.secret=your-secret-key
jwt.expiration=86400000
jwt.refresh.expiration=604800000
```

#### Конфігурація безпеки

Spring Security конфігурація для захисту ендпоінтів.

---

## auth-spi-adapter

Реалізації адаптерів для роботи з користувачами та автентифікацією.

### Адаптери

Конкретні реалізації інтерфейсів з auth-spi для взаємодії з базою даних та зовнішніми сервісами.

---

## auth-microservice

Головний мікросервіс - точка входу застосунку.

### AuthApplication

Spring Boot застосунок (main клас).

**Порт:** 8080

### Основний функціонал

- Реєстрація користувачів
- Автентифікація (login)
- Видача JWT токенів
- Оновлення токенів (refresh)
- Управління профілем користувача

---

## Швидкий старт

### 1. Налаштувати application.properties

`auth-microservice/src/main/resources/application.properties`:
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/maketo
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 2. Налаштувати jwt.properties

`auth-microservice/src/main/resources/jwt.properties`:
```properties
jwt.secret=your-very-long-secret-key-here
jwt.expiration=86400000
jwt.refresh.expiration=604800000
```

### 3. Запустити PostgreSQL

```bash
docker run -d --name postgres \
  -p 5432:5432 \
  -e POSTGRES_DB=maketo \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:15
```

### 4. Зібрати та запустити

```bash
mvn clean install
cd auth-microservice
mvn spring-boot:run
```

### 5. Перевірити

```bash
curl http://localhost:8080/actuator/health
```

---

## Приклади API

### Реєстрація

```bash
POST /auth/register
Content-Type: application/json

{
  "username": "ivan",
  "email": "ivan@example.com",
  "password": "securePassword123"
}
```

**Відповідь:**
```json
{
  "message": "Користувач успішно зареєстрований. Перевірте email для активації."
}
```

### Автентифікація

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "ivan@example.com",
  "password": "securePassword123"
}
```

**Відповідь:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 86400
}
```

### Оновлення токена

```bash
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

## Інтеграція з іншими сервісами

### Надсилання сповіщень

Auth-сервіс інтегрований з notification-сервісом через RabbitMQ для надсилання email сповіщень.

**При реєстрації користувача:**

```java
// Створюється UserDto
UserDto userDto = new UserDto(
    user.getName(),
    user.getEmail(),
    activationToken
);

// Надсилається в RabbitMQ
rabbitTemplate.convertAndSend(
    "maketo.exchange",
    "user.registration",
    userDto
);
```

notification-сервіс отримає повідомлення та надішле активаційний лист автоматично.

---

## Безпека

### Хешування паролів

Паролі хешуються з використанням BCrypt перед збереженням в базу даних.

```java
String hashedPassword = passwordEncoder.encode(rawPassword);
```

### JWT токени

**Access Token:**
- Час життя: 24 години (налаштовується)
- Використовується для доступу до захищених ендпоінтів
- Передається в заголовку: `Authorization: Bearer <token>`

**Refresh Token:**
- Час життя: 7 днів (налаштовується)
- Використовується для отримання нового Access Token
- Зберігається в безпечному місці на клієнті

### Захист ендпоінтів

Використовуйте анотацію `@PreAuthorize` для захисту методів:

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/users")
public List<User> getAllUsers() {
    return userService.findAll();
}
```

---

## Конфігурація

### application.properties

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/maketo
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin
```

### jwt.properties

```properties
jwt.secret=your-secret-key-min-256-bits
jwt.expiration=86400000
jwt.refresh.expiration=604800000
```

---

## Базова структура БД

### Таблиця Users

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    activation_token VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Таблиця Roles

```sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
```

### Таблиця User_Roles

```sql
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);
```

---

## Вирішення проблем

**Не підключається до БД:**
- Перевірте що PostgreSQL запущено: `docker ps`
- Перевірте connection string в application.properties
- Переконайтеся що база даних створена

**JWT токени не валідуються:**
- Перевірте що jwt.secret однаковий всюди
- Переконайтеся що токен не прострочений
- Перевірте формат токена (Bearer <token>)

**Email сповіщення не надсилаються:**
- Перевірте що RabbitMQ запущено
- Перевірте що notification-сервіс працює
- Перевірте логи обох сервісів

---

## Розвиток

### Заплановані функції

- [ ] OAuth2 інтеграція (Google, GitHub)
- [ ] Двофакторна автентифікація (2FA)
- [ ] Rate limiting для спроб входу
- [ ] Аудит логування дій користувачів
- [ ] Відновлення пароля через email
- [ ] Підтвердження email при реєстрації

