package org.example.backendjava.auth_service.model.dto;

import lombok.Data;

@Data
public class DoctorResumeResponseDto {

    private Long id;
    private Long doctorId;
    private String stage;
    private Integer experienceYears;
    private String education;
    private String certificates;
    private String photoUrl;
    private String description;
}


