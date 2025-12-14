package org.example.backendjava.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.mapper.DoctorResumeMapper;
import org.example.backendjava.auth_service.model.dto.DoctorResumeRequestDto;
import org.example.backendjava.auth_service.model.dto.DoctorResumeResponseDto;
import org.example.backendjava.auth_service.model.entity.Doctor;
import org.example.backendjava.auth_service.model.entity.DoctorResume;
import org.example.backendjava.auth_service.repository.DoctorRepository;
import org.example.backendjava.auth_service.repository.DoctorResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DoctorResumeService {

    private final DoctorResumeRepository repository;
    private final DoctorRepository doctorRepository;
    private final DoctorResumeMapper mapper;
    private final MinioService minioService;

    public DoctorResumeResponseDto create(Long doctorId, DoctorResumeRequestDto dto) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (repository.findByDoctorId(doctorId).isPresent()) {
            throw new RuntimeException("Resume already exists");
        }

        DoctorResume resume = mapper.toEntity(dto);
        resume.setDoctor(doctor);

        return mapper.toDto(repository.save(resume));
    }

    public DoctorResumeResponseDto get(Long doctorId) {
        return repository.findByDoctorId(doctorId)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    public DoctorResumeResponseDto update(Long doctorId, DoctorResumeRequestDto dto) {
        DoctorResume resume = repository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        mapper.update(resume, dto);
        return mapper.toDto(repository.save(resume));
    }

    public void delete(Long doctorId) {
        DoctorResume resume = repository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        if (resume.getPhotoUrl() != null) {
            deletePhotoInternal(resume);
        }

        repository.delete(resume);
    }


    public String uploadPhoto(Long doctorId, MultipartFile file) {

        DoctorResume resume = repository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        if (resume.getPhotoUrl() != null) {
            deletePhotoInternal(resume);
        }

        String objectName = "doctor-" + doctorId + "/avatar.jpg";
        String url = minioService.upload(file, objectName);

        resume.setPhotoUrl(url);
        repository.save(resume);

        return url;
    }

    public void deletePhoto(Long doctorId) {

        DoctorResume resume = repository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        deletePhotoInternal(resume);
        resume.setPhotoUrl(null);
        repository.save(resume);
    }

    private void deletePhotoInternal(DoctorResume resume) {
        String objectName = resume.getPhotoUrl()
                .replace("http://localhost:9000/", "")
                .replaceFirst(".+?/", "");
        minioService.delete(objectName);
    }
}
