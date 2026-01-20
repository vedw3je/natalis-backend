package dev.ved.natalis.mother_service.repository;

import dev.ved.natalis.mother_service.entity.Mother;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MotherRepository extends MongoRepository<Mother, String> {

    Optional<Mother> findByUserId(String userId);

    List<Mother> findByFullNameRegexIgnoreCase(String regex);

    Optional<Mother> findByEmergencyContact(String emergencyContact);
}