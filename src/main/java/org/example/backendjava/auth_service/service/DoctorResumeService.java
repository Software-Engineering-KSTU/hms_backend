package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.DoctorResumeDto;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.DoctorResume;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorResumeService {

    private final DoctorRepository doctorRepository;

    public DoctorResumeDto getResume(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        DoctorResume resume = doctor.getResume();

        DoctorResumeDto dto = new DoctorResumeDto();
        if (resume != null) {
            dto.setAboutMe(resume.getAboutMe());
            dto.setExperienceYears(resume.getExperienceYears());
            dto.setAge(resume.getAge());
            dto.setEducationJson(resume.getEducationJson());
            dto.setPhotoUrl(resume.getPhotoUrl());
        }
        dto.setFullName(doctor.getUser().getUsername());
        dto.setSpecialization(doctor.getSpecialization());
        return dto;
    }
}