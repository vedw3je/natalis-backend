package dev.ved.natalis.doctor_service.controller;

import dev.ved.natalis.doctor_service.entity.Doctor;
import dev.ved.natalis.doctor_service.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    // Constructor injection (preferred)
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors (){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor){
        return ResponseEntity.ok(doctorService.saveDoctor(doctor));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> findDoctorsByName(@RequestParam String name) {
        List<Doctor> doctors = doctorService.getDoctorsByName(name);
        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No doctors found with name: " + name);
        }
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/searchByCity")
    public ResponseEntity<?> findDoctorsByCity(@RequestParam String city) {
        List<Doctor> doctors = doctorService.getDoctorsByCity(city);
        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No doctors found in city: " + city);
        }
        return ResponseEntity.ok(doctors);
    }


    @GetMapping("/searchByPhoneNumber")
    public ResponseEntity<?> findDoctorByPhoneNumber(@RequestParam String phoneNumber) {
        Optional<Doctor> doctor = doctorService.getDoctorByPhoneNumber(phoneNumber);

        if (doctor.isPresent()) {
            return ResponseEntity.ok(doctor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No doctor found with phone number: " + phoneNumber);
        }
    }





}
