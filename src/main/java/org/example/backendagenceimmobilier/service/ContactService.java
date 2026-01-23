package org.example.backendagenceimmobilier.service;

import org.example.backendagenceimmobilier.model.ContactDemande;
import org.example.backendagenceimmobilier.repository.ContactDemandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactDemandeRepository contactRepository;

    public ContactDemande saveContact(ContactDemande contact) {
        return contactRepository.save(contact);
    }

    public List<ContactDemande> getAllContacts() {
        return contactRepository.findAllByOrderByDateDemandeDesc();
    }

    public List<ContactDemande> getContactsByStatut(String statut) {
        return contactRepository.findByStatutOrderByDateDemandeDesc(statut);
    }

    // Méthodes Admin
    public Optional<ContactDemande> updateStatut(Long id, String statut) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setStatut(statut);
                    return contactRepository.save(contact);
                });
    }
    public Optional<ContactDemande> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Optional<ContactDemande> updateContact(Long id, ContactDemande contactDetails) {
        return contactRepository.findById(id)
                .map(existing -> {
                    existing.setNom(contactDetails.getNom());
                    existing.setPrenom(contactDetails.getPrenom());
                    existing.setEmail(contactDetails.getEmail());
                    existing.setTelephone(contactDetails.getTelephone());
                    existing.setMessage(contactDetails.getMessage());
                    existing.setStatut(contactDetails.getStatut());
                    if (contactDetails.getBien() != null) {
                        existing.setBien(contactDetails.getBien());
                    }

                    return contactRepository.save(existing);
                });
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}