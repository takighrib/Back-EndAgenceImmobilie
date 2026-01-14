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

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}