package org.example.backendjava.auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.auth_service.model.dto.DoctorResumeDto;
import org.example.backendjava.auth_service.service.DoctorResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors")
public class DoctorController {

    private final DoctorResumeService doctorResumeService;

    @Operation(summary = "Получить резюме врача")
    @GetMapping("/{id}/resume")
    public ResponseEntity<DoctorResumeDto> getDoctorResume(@PathVariable Long id) {
        return ResponseEntity.ok(doctorResumeService.getResume(id));
    }
}
