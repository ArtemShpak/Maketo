package com.maketo.notification.spi;

/**
 * SPI (Service Provider Interface) для отправки email.
 * Позволяет изолировать бизнес-логику от конкретной реализации отправки писем.
 */
public interface EmailSender {

    /**
     * Отправляет HTML email
     *
     * @param to адрес получателя
     * @param subject тема письма
     * @param htmlContent HTML содержимое письма
     * @throws Exception если произошла ошибка при отправке
     */
    void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception;
}

