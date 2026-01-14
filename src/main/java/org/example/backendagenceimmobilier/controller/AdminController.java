package org.example.backendagenceimmobilier.controller;

import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.ContactDemande;
import org.example.backendagenceimmobilier.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        return ResponseEntity.ok(adminService.getAdminStats());
    }

    @GetMapping("/biens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BienImmobilier>> getAllBiensAdmin() {
        return ResponseEntity.ok(adminService.getAllBiens());
    }

    @GetMapping("/contacts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactDemande>> getAllContacts() {
        return ResponseEntity.ok(adminService.getAllContacts());
    }

    @GetMapping("/contacts/non-traites")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactDemande>> getContactsNonTraites() {
        return ResponseEntity.ok(adminService.getContactsNonTraites());
    }
}