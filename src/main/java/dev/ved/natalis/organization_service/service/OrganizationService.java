package dev.ved.natalis.organization_service.service;

import dev.ved.natalis.organization_service.entity.Organization;
import dev.ved.natalis.organization_service.repository.OrganizationRepository;
import dev.ved.natalis.organization_service.requests.OrganizationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    /* =========================
       CREATE ORGANIZATION
       ========================= */

    public Organization createOrganization(OrganizationRequest request) {

        Organization organization = new Organization();
        organization.setOrganizationId(generateOrganizationId());
        organization.setName(request.getName());
        organization.setType(request.getType());
        organization.setAddress(request.getAddress());
        organization.setIsActive(true);
        organization.setCreatedAt(Instant.now());

        return organizationRepository.save(organization);
    }

    /* =========================
       GET ACTIVE ORGANIZATIONS
       ========================= */

    public List<Organization> getAllActiveOrganizations() {
        return organizationRepository.findByIsActiveTrue();
    }

    public Page<Organization> getAllActiveOrganizations(Pageable pageable) {
        return organizationRepository.findByIsActiveTrue(pageable);
    }

    /* =========================
       GET ORGANIZATION BY ID
       ========================= */

    public Organization getByOrganizationId(String organizationId) {
        return organizationRepository
                .findByOrganizationIdAndIsActiveTrue(organizationId)
                .orElseThrow(() ->
                        new NoSuchElementException("Organization not found")
                );
    }

    /* =========================
       UPDATE ORGANIZATION
       ========================= */

    public Organization updateOrganization(
            String organizationId,
            OrganizationRequest request
    ) {
        Organization organization = getByOrganizationId(organizationId);

        organization.setName(request.getName());
        organization.setType(request.getType());
        organization.setAddress(request.getAddress());

        return organizationRepository.save(organization);
    }

    /* =========================
       SOFT DISABLE ORGANIZATION
       ========================= */

    public void deactivateOrganization(String organizationId) {
        Organization organization = getByOrganizationId(organizationId);
        organization.setIsActive(false);
        organizationRepository.save(organization);
    }

    /* =========================
       SEARCH
       ========================= */

    public List<Organization> searchOrganizations(String name) {
        String regex = ".*" + Pattern.quote(name) + ".*";
        return organizationRepository
                .findByNameRegexIgnoreCaseAndIsActiveTrue(regex);
    }

    /* =========================
       PRIVATE UTIL
       ========================= */

    private String generateOrganizationId() {
        return "ORG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
