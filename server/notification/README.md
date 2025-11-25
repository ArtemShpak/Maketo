# Notification Module Architecture

## 🎨 Диаграмма зависимостей модулей

```
┌─────────────────────────────────────────────────────────────────┐
│                    notification-microservice                    │
│                  (Spring Boot Application)                       │
│                                                                  │
│  - NotificationApplication.java (main)                          │
│  - application.yml                                               │
└────────────┬────────────────────────────────────────────────────┘
             │
             │ depends on (все модули)
             │
     ┌───────┴────────┬─────────────┬───────────────┐
     │                │             │               │
     ▼                ▼             ▼               ▼
┌─────────┐    ┌──────────┐  ┌──────────┐   ┌────────────────┐
│   API   │    │   CORE   │  │   SPI    │   │  SPI-ADAPTER   │
└─────────┘    └──────────┘  └──────────┘   └────────────────┘


┌──────────────────────────────────────────────────────────────────┐
│                      notification-api                            │
│                    (Contracts / Use Cases)                       │
│                                                                   │
│  📄 SendActivationEmailUseCase.java (interface)                 │
│  📄 UserActivationDto.java (record)                             │
│                                                                   │
│  Dependencies: NONE                                              │
└──────────────────────────────────────────────────────────────────┘
                             ▲
                             │ implements
                             │
┌──────────────────────────────────────────────────────────────────┐
│                      notification-core                           │
│                    (Business Logic Layer)                        │
│                                                                   │
│  📄 EmailService.java → implements SendActivationEmailUseCase   │
│  📄 TemplateService.java → Thymeleaf template processing        │
│  📄 UserMapper.java → DTO mapping                               │
│                                                                   │
│  Dependencies:                                                   │
│    ✅ notification-api (implements use cases)                   │
│    ✅ notification-spi (uses EmailSender interface)             │
│    ✅ spring-boot-starter-thymeleaf                             │
│    ❌ spring-boot-starter-mail (REMOVED!)                       │
└────────────────────┬─────────────────────────────────────────────┘
                     │
                     │ uses interface
                     │
                     ▼
┌──────────────────────────────────────────────────────────────────┐
│                      notification-spi                            │
│              (Service Provider Interface)                        │
│                                                                   │
│  📄 EmailSender.java (interface)                                │
│      └─ sendHtmlEmail(to, subject, htmlContent)                 │
│                                                                   │
│  Dependencies: NONE                                              │
└──────────────────────────────────────────────────────────────────┘
                     ▲
                     │ implements
                     │
┌──────────────────────────────────────────────────────────────────┐
│                   notification-spi-adapter                       │
│            (Infrastructure / Implementation Layer)               │
│                                                                   │
│  📁 mail/                                                        │
│    📄 JavaMailSenderAdapter.java → implements EmailSender       │
│    📄 config/MailConfig.java → creates JavaMailSender bean      │
│                                                                   │
│  📁 messaging/                                                   │
│    📄 AuthenticationListener.java → RabbitMQ listener           │
│    📄 config/RabbitAuthenticationConfig.java                    │
│                                                                   │
│  Dependencies:                                                   │
│    ✅ notification-spi (implements interface)                   │
│    ✅ notification-core (uses business logic)                   │
│    ✅ notification-api (uses use cases)                         │
│    ✅ spring-boot-starter-mail (JavaMailSender)                 │
│    ✅ common-messaging (RabbitMQ DTOs)                          │
└──────────────────────────────────────────────────────────────────┘
```

## 🔄 Поток выполнения (Flow)

```
1. RabbitMQ Event
        │
        │ user.registration.queue
        ▼
┌─────────────────────────────┐
│  AuthenticationListener     │  ← notification-spi-adapter
│  @RabbitListener            │
└────────────┬────────────────┘
             │
             │ calls
             ▼
┌─────────────────────────────┐
│  SendActivationEmailUseCase │  ← notification-api (interface)
└────────────┬────────────────┘
             │
             │ implemented by
             ▼
┌─────────────────────────────┐
│  EmailService               │  ← notification-core
│  - builds HTML template     │
└────────────┬────────────────┘
             │
             │ uses
             ▼
┌─────────────────────────────┐
│  EmailSender (interface)    │  ← notification-spi
└────────────┬────────────────┘
             │
             │ implemented by
             ▼
┌─────────────────────────────┐
│  JavaMailSenderAdapter      │  ← notification-spi-adapter
│  - sends via JavaMailSender │
└─────────────────────────────┘
             │
             │ uses
             ▼
┌─────────────────────────────┐
│  JavaMailSender (Spring)    │  ← Spring Framework
│  - SMTP connection          │
└─────────────────────────────┘
             │
             │ sends email via
             ▼
      📧 SMTP Server
   (Gmail, SendGrid, etc.)
```

## 🎯 Принципы Clean Architecture

### 1. Dependency Rule (Правило зависимостей)
```
API (interfaces) ← CORE (business logic) ← SPI (interfaces)
                                              ↑
                                    SPI-ADAPTER (implementation)
```

**Зависимости направлены ВНУТРЬ**, от деталей к абстракциям.

### 2. Independence (Независимость)

- ✅ **Business logic** (core) независима от инфраструктуры
- ✅ **Use cases** (api) независимы от реализации
- ✅ **SPI interfaces** определяют контракт
- ✅ **Adapters** можно заменять без изменения core

### 3. Testability (Тестируемость)

```java
// Можно тестировать EmailService с моком
EmailSender mockEmailSender = mock(EmailSender.class);
EmailService service = new EmailService(mockEmailSender, templateService);
```

## 🔧 Альтернативные реализации (примеры)

### SendGrid Adapter
```java
@Component
@ConditionalOnProperty(name = "email.provider", havingValue = "sendgrid")
public class SendGridAdapter implements EmailSender {
    private final SendGrid sendGridClient;
    
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        // SendGrid implementation
    }
}
```

### AWS SES Adapter
```java
@Component
@ConditionalOnProperty(name = "email.provider", havingValue = "aws-ses")
public class AwsSesAdapter implements EmailSender {
    private final AmazonSimpleEmailService sesClient;
    
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        // AWS SES implementation
    }
}
```

### Mock Adapter (для тестов)
```java
@Component
@Profile("test")
public class MockEmailSender implements EmailSender {
    private final List<SentEmail> sentEmails = new ArrayList<>();
    
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        sentEmails.add(new SentEmail(to, subject, htmlContent));
        System.out.println("📧 [MOCK] Email sent to: " + to);
    }
}
```

## ✅ Чек-лист правильной архитектуры

- [x] Core не зависит от конкретных технологий (JavaMailSender)
- [x] SPI определяет интерфейсы для внешних сервисов
- [x] Adapters реализуют SPI интерфейсы
- [x] DTO находятся в API модуле
- [x] Business logic изолирована и тестируема
- [x] Легко добавить новые реализации без изменения core
- [x] Соблюдены принципы SOLID (особенно DIP - Dependency Inversion)

---

**Создано**: 2025-11-25  
**Версия**: 1.0  
**Автор**: Refactoring для Clean Architecture

