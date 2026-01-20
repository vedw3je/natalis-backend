package dev.ved.natalis.organization_service.controller;

import dev.ved.natalis.organization_service.entity.Organization;
import dev.ved.natalis.organization_service.requests.OrganizationRequest;
import dev.ved.natalis.organization_service.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    /* =========================
       CREATE ORGANIZATION
       ========================= */

    @PostMapping
    public ResponseEntity<Organization> createOrganization(
            @Valid @RequestBody OrganizationRequest request
    ) {
        Organization organization = organizationService.createOrganization(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organization);
    }

    /* =========================
       GET ALL ACTIVE ORGANIZATIONS
       ========================= */

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        return ResponseEntity.ok(
                organizationService.getAllActiveOrganizations()
        );
    }

    /* =========================
       GET ALL (PAGINATED)
       ========================= */

    @GetMapping("/paged")
    public ResponseEntity<Page<Organization>> getOrganizationsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                organizationService.getAllActiveOrganizations(pageable)
        );
    }

    /* =========================
       GET BY ORGANIZATION ID
       ========================= */

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(
            @PathVariable String organizationId
    ) {
        return ResponseEntity.ok(
                organizationService.getByOrganizationId(organizationId)
        );
    }

    /* =========================
       UPDATE ORGANIZATION
       ========================= */

    @PutMapping("/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable String organizationId,
            @Valid @RequestBody OrganizationRequest request
    ) {
        return ResponseEntity.ok(
                organizationService.updateOrganization(organizationId, request)
        );
    }

    /* =========================
       SEARCH ORGANIZATION
       ========================= */

    @GetMapping("/search")
    public ResponseEntity<List<Organization>> searchOrganizations(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(
                organizationService.searchOrganizations(name)
        );
    }

    /* =========================
       DEACTIVATE ORGANIZATION
       ========================= */

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deactivateOrganization(
            @PathVariable String organizationId
    ) {
        organizationService.deactivateOrganization(organizationId);
        return ResponseEntity.noContent().build();
    }
}
