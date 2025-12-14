package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.DoctorResumeRequestDto;
import org.example.backendjava.auth_service.model.dto.DoctorResumeResponseDto;
import org.example.backendjava.auth_service.service.DoctorResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/doctor-resume")
@RequiredArgsConstructor
@Tag(name = "Doctor Resume", description = "CRUD резюме врача и управление фотографией")
public class DoctorResumeController {

    private final DoctorResumeService service;

    @PostMapping
    @Operation(summary = "Создать резюме врача")
    public ResponseEntity<DoctorResumeResponseDto> create(
            @Parameter(description = "ID врача", example = "1")
            @RequestParam Long doctorId,
            @RequestBody DoctorResumeRequestDto dto) {

        return ResponseEntity.ok(service.create(doctorId, dto));
    }

    @GetMapping("/{doctorId}")
    @Operation(summary = "Получить резюме врача")
    public ResponseEntity<DoctorResumeResponseDto> get(
            @Parameter(description = "ID врача", example = "1")
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(service.get(doctorId));
    }

    @PutMapping("/{doctorId}")
    @Operation(summary = "Обновить резюме врача")
    public ResponseEntity<DoctorResumeResponseDto> update(
            @Parameter(description = "ID врача", example = "1")
            @PathVariable Long doctorId,
            @RequestBody DoctorResumeRequestDto dto) {

        return ResponseEntity.ok(service.update(doctorId, dto));
    }

    @DeleteMapping("/{doctorId}")
    @Operation(summary = "Удалить резюме врача")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID врача", example = "1")
            @PathVariable Long doctorId) {

        service.delete(doctorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{doctorId}/photo")
    @Operation(summary = "Загрузить или заменить фото врача")
    public ResponseEntity<String> uploadPhoto(
            @Parameter(description = "ID врача", example = "1")
            @PathVariable Long doctorId,
            @Parameter(description = "Файл изображения (jpg/png)")
            @RequestParam MultipartFile file) {

        return ResponseEntity.ok(service.uploadPhoto(doctorId, file));
    }

    @DeleteMapping("/{doctorId}/photo")
    @Operation(summary = "Удалить фото врача")
    public ResponseEntity<Void> deletePhoto(
            @Parameter(description = "ID врача", example = "1")
            @PathVariable Long doctorId) {

        service.deletePhoto(doctorId);
        return ResponseEntity.noContent().build();
    }
}
