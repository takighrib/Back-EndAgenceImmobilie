package org.example.backendagenceimmobilier.controller;

import org.example.backendagenceimmobilier.model.ContactDemande;
import org.example.backendagenceimmobilier.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDemande> createContact(@Valid @RequestBody ContactDemande contact) {
        ContactDemande saved = contactService.saveContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ContactDemande>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContactDemande> getContactById(@PathVariable Long id) {
        return contactService.getContactById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT pour mettre à jour un contact complet
    @PutMapping("/{id}")
    public ResponseEntity<ContactDemande> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDemande contact) {
        return contactService.updateContact(id, contact)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ContactDemande>> getContactsByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(contactService.getContactsByStatut(statut));
    }

    // Endpoints Admin
    @PutMapping("/{id}/statut")
    public ResponseEntity<ContactDemande> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        return contactService.updateStatut(id, statut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok().build();
    }
}