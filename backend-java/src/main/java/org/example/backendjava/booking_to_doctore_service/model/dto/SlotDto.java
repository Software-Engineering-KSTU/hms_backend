package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
    LocalDateTime dateTime;
    String busyStatus;
}

