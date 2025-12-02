package org.example.backendjava.doctor_cabinet_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentRequestDto;
import org.example.backendjava.doctor_cabinet_service.model.dto.DepartmentResponseDto;
import org.example.backendjava.doctor_cabinet_service.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> create(
            @RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> update(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}