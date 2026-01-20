package dev.ved.natalis.tests_service.controller;

import dev.ved.natalis.tests_service.dto.MedicalTestResponse;
import dev.ved.natalis.tests_service.entity.MedicalTest;
import dev.ved.natalis.tests_service.repository.MedicalTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class MedicalTestController {

    @Autowired
    private MedicalTestRepository medicalTestRepository;

    @PostMapping("/create")
    public ResponseEntity<MedicalTestResponse> createTest(@RequestBody MedicalTest test) {
        MedicalTest savedTest = medicalTestRepository.save(test);

        MedicalTestResponse response = new MedicalTestResponse(
                savedTest.getId(),
                savedTest.getMotherId(),
                savedTest.getTestName(),
                savedTest.getDescription(),
                savedTest.getAppointmentDate(),
                savedTest.getStatus(),
                savedTest.getResultSummary(),
                savedTest.getLaboratoryName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicalTestResponse>> getAllTests() {
        List<MedicalTest> tests = medicalTestRepository.findAll();

        List<MedicalTestResponse> response = tests.stream()
                .map(test -> new MedicalTestResponse(
                        test.getId(),
                        test.getMotherId(),
                        test.getTestName(),
                        test.getDescription(),
                        test.getAppointmentDate(),
                        test.getStatus(),
                        test.getResultSummary(),
                        test.getLaboratoryName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}