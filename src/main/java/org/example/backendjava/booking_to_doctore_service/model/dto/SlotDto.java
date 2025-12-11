package org.example.backendjava.booking_to_doctore_service.model.dto;

import lombok.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
    LocalDateTime dateTime;
    String busyStatus;

    @Override
    public String toString() {
        return "SlotDto{" +
                "dateTime=" + dateTime +
                ", busyStatus='" + busyStatus + '\'' +
                '}';
    }
}

