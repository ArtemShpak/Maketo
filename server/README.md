# Maketo Server

<div align="center">

**Мікросервісна архітектура серверної частини платформи Maketo**

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-4.0.0-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Proprietary-red.svg)]()

</div>

---

## 📋 Зміст

- [Про проект](#-про-проект)
- [Архітектура](#-архітектура)
- [Технології](#-технології)
- [Структура проекту](#-структура-проекту)
- [Встановлення та запуск](#-встановлення-та-запуск)
- [Мікросервіси](#-мікросервіси)
- [Конфігурація](#-конфігурація)
- [Розробка](#-розробка)
- [API документація](#-api-документація)

---

## 🎯 Про проект

**Maketo Server** — це серверна частина проекту, реалізована з використанням мікросервісної архітектури на базі Spring Boot 3.4.4 та Java 21.

Система складається з незалежних мікросервісів, які взаємодіють між собою для забезпечення повного функціоналу платформи.

### Основні можливості:

✅ **Автентифікація та авторизація** — JWT-based аутентифікація  
✅ **Система сповіщень** — Email та внутрішні повідомлення  
✅ **Модульна архітектура** — Hexagonal Architecture (Ports & Adapters)  
✅ **Масштабованість** — Незалежне розгортання сервісів  

---

## 🏗 Архітектура

Проект побудований з використанням **Hexagonal Architecture** (Ports and Adapters) для забезпечення гнучкості та можливості тестування.

```
┌─────────────────────────────────────────────────────┐
│                   Maketo Platform                    │
├──────────────────────┬──────────────────────────────┤
│   Auth Service       │   Notification Service        │
│   (Port: 8080)       │   (Port: 8081)               │
├──────────────────────┴──────────────────────────────┤
│              PostgreSQL Database                     │
│              (Port: 5432)                           │
└─────────────────────────────────────────────────────┘
```

### Принципи архітектури кожного мікросервісу:

```
┌─────────────────────────────────────────────┐
│              API Layer (REST)                │  ← Контролери
├─────────────────────────────────────────────┤
│             Core (Business Logic)            │  ← Бізнес-логіка
├─────────────────────────────────────────────┤
│         SPI (Service Provider Interface)     │  ← Інтерфейси
├─────────────────────────────────────────────┤
│         SPI Adapter (Implementation)         │  ← Реалізація
├─────────────────────────────────────────────┤
│         Microservice (Entry Point)           │  ← Точка входу
└─────────────────────────────────────────────┘
```

---

## 🛠 Технології

### Backend Stack:

| Технологія | Версія | Призначення |
|-----------|--------|------------|
| **Java** | 21 | Мова програмування |
| **Spring Boot** | 3.4.4 | Фреймворк для створення мікросервісів |
| **Spring Data JPA** | - | Робота з базою даних |
| **Spring Security** | - | Безпека та аутентифікація |
| **PostgreSQL** | - | Реляційна база даних |
| **Lombok** | 1.18.36 | Зменшення boilerplate коду |
| **Maven** | 4.0.0 | Система збірки |
| **JWT** | - | Токени доступу |

### Додаткові інструменти:

- **Spring Boot Actuator** — моніторинг та метрики
- **Hibernate** — ORM для роботи з БД

---

## 📁 Структура проекту

```
server/
│
├── auth/                          # Модуль автентифікації
│   ├── auth-api/                  # REST API контролери
│   ├── auth-core/                 # Бізнес-логіка
│   ├── auth-spi/                  # Інтерфейси (Ports)
│   ├── auth-spi-adapter/          # Адаптери (Adapters)
│   └── auth-microservice/         # Головний мікросервіс (8080)
│
├── notification/                  # Модуль сповіщень
│   ├── notification-api/          # REST API контролери
│   ├── notification-core/         # Бізнес-логіка
│   ├── notification-spi/          # Інтерфейси (Ports)
│   ├── notification-spi-adapter/  # Адаптери (Adapters)
│   └── notification-microservice/ # Головний мікросервіс (8081)
│
├── pom.xml                        # Головний Maven конфіг
├── mvnw / mvnw.cmd               # Maven Wrapper
└── README.md                      # Цей файл
```

### Опис шарів кожного модуля:

- **`*-api`** — REST контролери, HTTP endpoints
- **`*-core`** — Бізнес-логіка, use cases, domain моделі
- **`*-spi`** — Service Provider Interfaces (контракти/порти)
- **`*-spi-adapter`** — Реалізації інтерфейсів (адаптери до БД, зовнішніх API)
- **`*-microservice`** — Точка входу, Spring Boot Application, конфігурація

---

## 🚀 Встановлення та запуск

### Вимоги:

- **Java 21** або вище
- **Maven 3.6+** (або використовуйте Maven Wrapper)
- **PostgreSQL 12+**
- **Git**

### Крок 1: Клонування репозиторію

```bash
git clone <repository-url>
cd server
```

### Крок 2: Налаштування бази даних

Створіть базу даних PostgreSQL:

```sql
CREATE DATABASE Maketo;
```

### Крок 3: Конфігурація

Відредагуйте файл `auth/auth-microservice/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Maketo
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Крок 4: Збірка проекту

```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

### Крок 5: Запуск мікросервісів

#### Запуск Auth Service:

```bash
cd auth/auth-microservice
../../mvnw.cmd spring-boot:run
```

Сервіс буде доступний на `http://localhost:8080`

#### Запуск Notification Service:

```bash
cd notification/notification-microservice
../../mvnw.cmd spring-boot:run
```

Сервіс буде доступний на `http://localhost:8081`

---

## 🎯 Мікросервіси

### 1️⃣ Auth Service (Порт: 8080)

**Призначення:** Управління користувачами, автентифікація та авторизація.

**Основні можливості:**
- ✅ Реєстрація користувачів
- ✅ Вхід в систему (Login)
- ✅ Генерація JWT токенів
- ✅ Валідація токенів
- ✅ Управління користувачами

**Технологічний стек:**
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL

**Endpoints:**
- `POST /auth/register` — Реєстрація
- `POST /auth/login` — Вхід
- `GET /auth/user` — Отримання інформації про користувача

Детальніше: [auth/README.md](auth/README.md)

---

### 2️⃣ Notification Service (Порт: 8081)

**Призначення:** Управління сповіщеннями користувачів.

**Основні можливості:**
- ✅ Надсилання Email повідомлень
- ✅ Внутрішні сповіщення (In-App)
- ✅ Інтеграція з RabbitMQ (планується)
- ✅ Шаблони повідомлень

**Технологічний стек:**
- Spring Mail
- RabbitMQ (планується)
- Template Engine

**Endpoints:**
- `GET /notification/health` — Перевірка працездатності
- `POST /notification/email` — Надсилання Email

Детальніше: [notification/README.md](notification/README.md)

---

## ⚙️ Конфігурація

### Основні конфігураційні файли:

#### Auth Service (`application.properties`):
```properties
# Сервер
spring.application.name=auth-microservice
server.port=8080

# База даних
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/Maketo
spring.datasource.username=postgres
spring.datasource.password=your_password

# JWT (jwt.properties)
spring.config.import=jwt.properties

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

#### Notification Service (`application.properties`):
```properties
# Сервер
spring.application.name=notification-microservice
server.port=8081

# Email (якщо використовується)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-password
```

---

## 👨‍💻 Розробка

### Структура модуля (приклад):

```java
// SPI (Interface/Port)
public interface UserRepository {
    User findByEmail(String email);
    User save(User user);
}

// SPI Adapter (Implementation)
@Repository
public class UserRepositoryAdapter implements UserRepository {
    // JPA implementation
}

// Core (Business Logic)
@Service
public class AuthService {
    private final UserRepository userRepository;
    
    public User register(RegisterDto dto) {
        // Business logic
    }
}

// API (Controller)
@RestController
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
}
```

### Додавання нового модуля:

1. Створіть папку в корені проекту
2. Додайте `<module>` в головний `pom.xml`
3. Створіть структуру: `api`, `core`, `spi`, `spi-adapter`, `microservice`
4. Налаштуйте залежності між модулями

### Запуск тестів:

```bash
mvnw.cmd test
```

---

## 📚 API Документація

### Health Checks:

- Auth Service: `http://localhost:8080/actuator/health`
- Notification Service: `http://localhost:8081/notification/health`

### API Endpoints:

Детальну документацію по кожному мікросервісу можна знайти в відповідних README файлах:

- [Auth API Documentation](auth/README.md)
- [Notification API Documentation](notification/README.md)

---

## 🔒 Безпека

- **JWT Authentication** — Всі захищені endpoints вимагають валідний JWT токен
- **Password Encryption** — Паролі зберігаються в зашифрованому вигляді
- **CORS** — Налаштована політика CORS для безпечної взаємодії з фронтендом

---

## 📝 Додаткова інформація

### Maven команди:

```bash
# Збірка без тестів
mvnw.cmd clean install -DskipTests

# Збірка конкретного модуля
mvnw.cmd clean install -pl auth

# Запуск конкретного мікросервісу
mvnw.cmd spring-boot:run -pl auth/auth-microservice
```

### Корисні посилання:

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [PostgreSQL](https://www.postgresql.org/)

---

## 📄 Ліцензія

Цей проект є частиною курсової роботи. Всі права захищені.

---

## 👤 Автор

**Artem**  
Курсова робота  
2024-2025 навчальний рік

---

<div align="center">

**Made with ❤️ using Java & Spring Boot**

</div>
