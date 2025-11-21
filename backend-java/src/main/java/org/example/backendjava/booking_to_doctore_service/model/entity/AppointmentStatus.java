package org.example.backendjava.booking_to_doctore_service.model.entity;

/**
 *Статусы для записи к врачу.
 */
public enum AppointmentStatus {
    SCHEDULED,      // Запланирована - пациент записался, ждет приема
    IN_PROGRESS,    // В процессе - прием идет прямо сейчас
    COMPLETED,      // Завершена - прием закончился
    CANCELLED,      // Отменена - пациент или врач отменили
    NO_SHOW         // Не явился - пациент не пришел на прием
}