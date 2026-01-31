package dev.ved.natalis.mother_service.service;

import dev.ved.natalis.mother_service.entity.Mother;
import dev.ved.natalis.mother_service.repository.MotherRepository;
import dev.ved.natalis.mother_service.requests.MotherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MotherService {

    private final MotherRepository motherRepository;

    /* =========================
       CREATE MOTHER
       ========================= */

    public Mother createMother(
            String organizationId,
            String doctorId,
            String userId,              // optional (can be null)
            MotherRequest request
    ) {

        if (userId != null && motherRepository.existsByUserId(userId)) {
            throw new IllegalStateException(
                    "Mother profile already exists for this user"
            );
        }

        Mother mother = new Mother();
        mother.setName(request.getName());
        mother.setAge(request.getAge());
        mother.setMaritalStatus(request.getMaritalStatus());
        mother.setBloodGroup(request.getBloodGroup());

        mother.setLmp(request.getLmp());

        mother.setEdd(calculateEdd(request.getLmp()));

        mother.setGravida(request.getGravida());
        mother.setPara(request.getPara());
        mother.setHighRisk(
                request.getHighRisk() != null ? request.getHighRisk() : false
        );

        mother.setAdditionalMedicalInfo(
                request.getAdditionalMedicalInfo()
        );

        mother.setOrganizationId(organizationId);
        mother.setDoctorId(doctorId);
        mother.setUserId(userId);

        mother.setIsActive(true);
        mother.setCreatedAt(Instant.now());
        return motherRepository.save(mother);
    }

    /* =========================
       GET MOTHERS
       ========================= */

    public Mother getMotherById(
            String motherId,
            String organizationId
    ) {
        return motherRepository
                .findByIdAndOrganizationId(motherId, organizationId)
                .orElseThrow(() ->
                        new NoSuchElementException("Mother not found")
                );
    }

    public List<Mother> getActiveMothersByOrganization(
            String organizationId
    ) {
        return motherRepository
                .findByOrganizationIdAndIsActiveTrue(organizationId);
    }

    public List<Mother> getActiveMothersByDoctor(
            String organizationId,
            String doctorId
    ) {
        return motherRepository
                .findByOrganizationIdAndDoctorIdAndIsActiveTrue(
                        organizationId, doctorId
                );
    }

    public Mother getMotherByUserId(String userId) {
        return motherRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new NoSuchElementException("Mother not found for user")
                );
    }

    /* =========================
       UPDATE MOTHER
       ========================= */

    public Mother updateMother(
            String motherId,
            String organizationId,
            MotherRequest request
    ) {
        Mother mother = getMotherById(motherId, organizationId);

        mother.setName(request.getName());
        mother.setAge(request.getAge());
        mother.setMaritalStatus(request.getMaritalStatus());
        mother.setBloodGroup(request.getBloodGroup());

        if (!mother.getLmp().equals(request.getLmp())) {
            mother.setLmp(request.getLmp());
            mother.setEdd(calculateEdd(request.getLmp()));
        }

        mother.setGravida(request.getGravida());
        mother.setPara(request.getPara());
        mother.setHighRisk(request.getHighRisk());

        mother.setAdditionalMedicalInfo(
                request.getAdditionalMedicalInfo()
        );

        return motherRepository.save(mother);
    }

    /* =========================
       SEARCH
       ========================= */

    public List<Mother> searchMothersByName(
            String organizationId,
            String name
    ) {
        String regex = ".*" + Pattern.quote(name) + ".*";
        return motherRepository
                .findByOrganizationIdAndNameRegexIgnoreCaseAndIsActiveTrue(
                        organizationId, regex
                );
    }

    /* =========================
       SOFT DELETE
       ========================= */

    public void deactivateMother(
            String motherId,
            String organizationId
    ) {
        Mother mother = getMotherById(motherId, organizationId);
        mother.setIsActive(false);
        motherRepository.save(mother);
    }

    /* =========================
       UTIL
       ========================= */

    private LocalDate calculateEdd(LocalDate lmp) {
        return lmp.plusWeeks(40);
    }
}
