package dev.ved.natalis.doctor_service.controller;

import dev.ved.natalis.doctor_service.dto.DoctorResponse;
import dev.ved.natalis.doctor_service.entity.Doctor;
import dev.ved.natalis.doctor_service.requests.DoctorRequest;
import dev.ved.natalis.doctor_service.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /* =========================
       CREATE DOCTOR
       ========================= */

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(
            @RequestParam String userId,
            @RequestParam String organizationId,
            @RequestParam String organizationName,
            @Valid @RequestBody DoctorRequest request
    ) {
        Doctor doctor = doctorService.createDoctor(
                userId,
                organizationId,
                organizationName,
                mapToEntity(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapToResponse(doctor));
    }


    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getDoctors(
            @RequestParam String organizationId
    ) {
        List<DoctorResponse> doctors = doctorService
                .getActiveDoctorsByOrganization(organizationId)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(doctors);
    }



    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable String doctorId,
            @RequestParam String organizationId
    ) {
        Doctor doctor = doctorService.getDoctorById(doctorId, organizationId);
        return ResponseEntity.ok(mapToResponse(doctor));
    }



    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponse>> searchDoctors(
            @RequestParam String organizationId,
            @RequestParam String name
    ) {
        List<DoctorResponse> doctors = doctorService
                .searchDoctorsByName(organizationId, name)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(doctors);
    }

    /* =========================
       UPDATE DOCTOR
       ========================= */

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable String doctorId,
            @RequestParam String organizationId,
            @Valid @RequestBody DoctorRequest request
    ) {
        Doctor doctor = doctorService.updateDoctor(
                doctorId,
                organizationId,
                mapToEntity(request)
        );

        return ResponseEntity.ok(mapToResponse(doctor));
    }

    /* =========================
       DEACTIVATE DOCTOR
       ========================= */

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deactivateDoctor(
            @PathVariable String doctorId,
            @RequestParam String organizationId
    ) {
        doctorService.deactivateDoctor(doctorId, organizationId);
        return ResponseEntity.noContent().build();
    }

    /* =========================
       MAPPERS
       ========================= */

    private Doctor mapToEntity(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        return doctor;
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .qualification(doctor.getQualification())
                .experienceYears(doctor.getExperienceYears())
                .organizationName(doctor.getOrganizationName())
                .build();
    }
}
