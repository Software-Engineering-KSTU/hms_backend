package org.example.backendjava.doctor_cabinet_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backendjava.doctor_cabinet_service.model.dto.VisitRequestDTO;
import org.example.backendjava.doctor_cabinet_service.model.dto.VisitResponseDTO;
import org.example.backendjava.doctor_cabinet_service.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
@Tag(
        name = "Visits",
        description = "Приёмы пациентов"
)
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    @Operation(
            summary = "Создать приём"
    )
    public VisitResponseDTO create(@RequestBody VisitRequestDTO dto) {
        return visitService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить приём"
    )
    public VisitResponseDTO update(@PathVariable Long id,
                                   @RequestBody VisitRequestDTO dto) {
        return visitService.update(id, dto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить приём по ID"
    )
    public VisitResponseDTO get(@PathVariable Long id) {
        return visitService.get(id);
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(
            summary = "Приёмы врача"
    )
    public List<VisitResponseDTO> byDoctor(@PathVariable Long doctorId) {
        return visitService.getByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Приёмы пациента"
    )
    public List<VisitResponseDTO> byPatient(@PathVariable Long patientId) {
        return visitService.getByPatient(patientId);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить приём"
    )
    public void delete(@PathVariable Long id) {
        visitService.delete(id);
    }
}
