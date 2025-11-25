// auth_service/src/main/java/org/example/backendjava/auth_service/model/dto/DoctorResumeDto.java

package org.example.backendjava.auth_service.model.dto;

import lombok.Data;

@Data
public class DoctorResumeDto {
    private String aboutMe;
    private Integer experienceYears;
    private Integer age;
    private String educationJson;
    private String photoUrl;
    private String fullName;
    private String specialization;
}