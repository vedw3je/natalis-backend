package dev.ved.natalis.test_service.controller;

import dev.ved.natalis.test_service.dto.TestResponse;
import dev.ved.natalis.test_service.dto.TestListResponse;
import dev.ved.natalis.test_service.entity.Test;
import dev.ved.natalis.test_service.requests.TestRequest;
import dev.ved.natalis.test_service.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    /* =========================
       CREATE TEST (Flask → Spring)
       ========================= */

    @PostMapping
    public ResponseEntity<TestResponse> createTest(
            @RequestParam String organizationId,
            @RequestParam String motherId,
            @RequestParam String motherName,
            @RequestParam String doctorId,
            @Valid @RequestBody TestRequest request
    ) {
        Test test = testService.createTest(
                organizationId,
                motherName,
                motherId,
                doctorId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapToResponse(test));
    }

    /* =========================
       GET TEST BY ID
       ========================= */

    @GetMapping("/{testId}")
    public ResponseEntity<TestResponse> getTestById(
            @PathVariable String testId,
            @RequestParam String organizationId
    ) {
        Test test = testService.getTestById(testId, organizationId);
        return ResponseEntity.ok(mapToResponse(test));
    }



/* =========================
   GET LATEST TEST BY MOTHER
   ========================= */

    @GetMapping("/by-mother")
    public ResponseEntity<TestResponse> getLatestTestByMother(
            @RequestParam String organizationId,
            @RequestParam String motherId
    ) {
        Test test = testService.getLatestTestByMother(
                organizationId,
                motherId
        );

        return ResponseEntity.ok(mapToResponse(test));
    }



    /* =========================
       GET TESTS BY DOCTOR
       ========================= */

    @GetMapping("/by-doctor")
    public ResponseEntity<List<TestResponse>> getTestsByDoctor(
            @RequestParam String organizationId,
            @RequestParam String doctorId
    ) {
        List<TestResponse> tests = testService
                .getTestsByDoctor(organizationId, doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(tests);
    }

    /* =========================
   LIST TESTS BY DOCTOR (MINI)
   ========================= */

    @GetMapping("/list-by-doctor")
    public ResponseEntity<List<TestListResponse>> listTestsByDoctor(
            @RequestParam String organizationId,
            @RequestParam String doctorId
    ) {
        List<TestListResponse> tests = testService
                .getTestsByDoctor(organizationId, doctorId)
                .stream()
                .map(this::mapToListResponse)
                .toList();

        return ResponseEntity.ok(tests);
    }


    /* =========================
       DELETE (SOFT)
       ========================= */

    @DeleteMapping("/{testId}")
    public ResponseEntity<Void> deactivateTest(
            @PathVariable String testId,
            @RequestParam String organizationId
    ) {
        testService.deactivateTest(testId, organizationId);
        return ResponseEntity.noContent().build();
    }

    /* =========================
       MAPPER
       ========================= */

    private TestResponse mapToResponse(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .motherId(test.getMotherId())
                .motherName(test.getMotherName())
                .doctorId(test.getDoctorId())
                .organizationId(test.getOrganizationId())
                .testType(test.getTestType())
                .testTime(test.getTestTime())
                .hcMm(test.getHcMm())
                .gaWeeks(test.getGaWeeks())
                .classification(test.getClassification())
                .percentileBand(test.getPercentileBand())
                .edd(test.getEdd())
                .trimester(test.getTrimester())
                .weeksRemaining(test.getWeeksRemaining())
                .annotatedImageBase64(test.getAnnotatedImageBase64())
                .build();
    }
    private TestListResponse mapToListResponse(Test test) {
        return TestListResponse.builder()
                .id(test.getId())
                .motherId(test.getMotherId())
                .motherName(test.getMotherName())
                .testType(test.getTestType()) // enum → enum3
                .testTime(test.getTestTime())
                .classification(test.getClassification())
                .trimester(test.getTrimester())
                .build();
    }


}

