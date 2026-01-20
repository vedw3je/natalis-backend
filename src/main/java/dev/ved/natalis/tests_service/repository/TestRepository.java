package dev.ved.natalis.tests_service.repository;

import dev.ved.natalis.tests_service.entity.MedicalTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalTestRepository extends MongoRepository<MedicalTest, String> {

    List<MedicalTest> findByMotherId(String motherId);

    List<MedicalTest> findByStatus(String status);

    List<MedicalTest> findByMotherIdAndStatus(String motherId, String status);

    List<MedicalTest> findByTestNameRegexIgnoreCase(String regex);
}