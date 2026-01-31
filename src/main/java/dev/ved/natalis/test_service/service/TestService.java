package dev.ved.natalis.test_service.service;

import dev.ved.natalis.test_service.entity.Test;
import dev.ved.natalis.test_service.enums.TestType;
import dev.ved.natalis.test_service.repository.TestRepository;
import dev.ved.natalis.test_service.requests.TestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    /* =========================
       CREATE TEST
       ========================= */

    public Test createTest(
            String organizationId,
            String motherName,
            String motherId,
            String doctorId,
            TestRequest request
    ) {

        Test test = new Test();

        /* --- Relationships --- */
        test.setOrganizationId(organizationId);
        test.setMotherName(motherName);
        test.setMotherId(motherId);
        test.setDoctorId(doctorId);

        /* --- Metadata --- */
        test.setTestType(TestType.AI_ANALYSIS);
        test.setTestTime(Instant.now());

        /* --- AI Output --- */
        test.setHcMm(request.getHcMm());
        test.setGaWeeks(request.getGaWeeks());
        test.setClassification(request.getClassification());
        test.setPercentileBand(request.getPercentileBand());
        test.setEdd(request.getEdd());
        test.setTrimester(request.getTrimester());
        test.setWeeksRemaining(request.getWeeksRemaining());
        test.setAnnotatedImageBase64(request.getAnnotatedImageBase64());
        test.setAdditionalResults(request.getAdditionalResults());

        /* --- System fields --- */
        test.setIsActive(true);
        test.setCreatedAt(Instant.now());

        return testRepository.save(test);
    }

    /* =========================
       GET TEST BY ID
       ========================= */

    public Test getTestById(
            String testId,
            String organizationId
    ) {
        return testRepository
                .findByIdAndOrganizationId(testId, organizationId)
                .orElseThrow(() ->
                        new NoSuchElementException("Test not found")
                );
    }

    /* =========================
       MOTHER TEST HISTORY
       ========================= */

    public List<Test> getTestsByMother(
            String organizationId,
            String motherId
    ) {
        return testRepository
                .findByOrganizationIdAndMotherIdAndIsActiveTrueOrderByTestTimeDesc(
                        organizationId, motherId
                );
    }

    /* =========================
       DOCTOR DASHBOARD
       ========================= */

    public List<Test> getTestsByDoctor(
            String organizationId,
            String doctorId
    ) {
        return testRepository
                .findByOrganizationIdAndDoctorIdAndIsActiveTrueOrderByTestTimeDesc(
                        organizationId, doctorId
                );
    }

    /* =========================
       ANALYTICS
       ========================= */

    public long countTestsByOrganization(String organizationId) {
        return testRepository
                .countByOrganizationIdAndIsActiveTrue(organizationId);
    }

    public long countTestsByClassification(
            String organizationId,
            String classification
    ) {
        return testRepository
                .countByOrganizationIdAndClassificationAndIsActiveTrue(
                        organizationId, classification
                );
    }

    /* =========================
       SOFT DELETE
       ========================= */

    public void deactivateTest(
            String testId,
            String organizationId
    ) {
        Test test = getTestById(testId, organizationId);
        test.setIsActive(false);
        testRepository.save(test);
    }
}
