package dev.ved.natalis.test_service.repository;

import dev.ved.natalis.test_service.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

    /* =========================
       CORE LOOKUPS
       ========================= */

    Optional<Test> findByIdAndOrganizationId(String id, String organizationId);

    /* =========================
       MOTHER TEST HISTORY
       ========================= */

    List<Test> findByOrganizationIdAndMotherIdAndIsActiveTrueOrderByTestTimeDesc(
            String organizationId,
            String motherId
    );

    /* =========================
       DOCTOR DASHBOARD
       ========================= */

    List<Test> findByOrganizationIdAndDoctorIdAndIsActiveTrueOrderByTestTimeDesc(
            String organizationId,
            String doctorId
    );

    /* =========================
       ORGANIZATION ANALYTICS
       ========================= */

    long countByOrganizationIdAndIsActiveTrue(String organizationId);

    long countByOrganizationIdAndClassificationAndIsActiveTrue(
            String organizationId,
            String classification
    );

    Optional<Test> findTopByOrganizationIdAndMotherIdAndIsActiveTrueOrderByTestTimeDesc(
            String organizationId,
            String motherId
    );

//    List<Test> findByOrganizationIdAndVisitIdAndIsActiveTrueOrderByTestTimeDesc(
//            String organizationId,
//            String visitId
//    );

}
