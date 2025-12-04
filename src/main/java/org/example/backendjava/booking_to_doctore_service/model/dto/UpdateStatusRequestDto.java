package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.backendjava.booking_to_doctore_service.model.entity.AppointmentStatus;

@Getter
@Setter
public class UpdateStatusRequestDto {
    private AppointmentStatus status;
}