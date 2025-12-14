package org.example.backendjava.auth_service.model.dto;

import lombok.Data;

@Data
public class DoctorResumeRequestDto {

    private String stage;
    private Integer experienceYears;
    private String education;
    private String certificates;
    private String description;
}

