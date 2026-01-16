package dev.ved.natalis.doctor_service.repository;
import dev.ved.natalis.doctor_service.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor,String> {

    public Optional<Doctor> findByPhoneNumber(String phoneNumber);

    public Optional<Doctor> findByEmail(String email);

    public List<Doctor> findByName(String name);
    public List<Doctor> findByNameRegexIgnoreCase(String regex);
    public List<Doctor> findByCity(String city);


}

