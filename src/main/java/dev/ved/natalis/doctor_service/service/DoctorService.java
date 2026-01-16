package dev.ved.natalis.doctor_service.service;
import dev.ved.natalis.doctor_service.entity.Doctor;
import dev.ved.natalis.doctor_service.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Create a new doctor
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get doctor by ID
    public Optional<Doctor> getDoctorById(String id) {
        return doctorRepository.findById(id);
    }

    public Doctor updateDoctor(String id, Doctor updatedDoctor) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setName(updatedDoctor.getName());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
            doctor.setQualification(updatedDoctor.getQualification());
            doctor.setExperienceYears(updatedDoctor.getExperienceYears());
            doctor.setHospitalName(updatedDoctor.getHospitalName());
            doctor.setHospitalAddress(updatedDoctor.getHospitalAddress());
            doctor.setAvailable(updatedDoctor.isAvailable());
            return doctorRepository.save(doctor);
        }

        return null; // Will be handled in GlobalExceptionHandler
    }


    // Delete doctor
    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    // Find by phone number
    public Optional<Doctor> getDoctorByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumber(phoneNumber);
    }

    // Find by email
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public List<Doctor> getDoctorsByName(String name) {
        String regex = ".*" + name + ".*"; // matches anywhere in the name
        return doctorRepository.findByNameRegexIgnoreCase(regex);
    }

    public List<Doctor> getDoctorsByCity(String city){
        return doctorRepository.findByCity(city);
    }


}
