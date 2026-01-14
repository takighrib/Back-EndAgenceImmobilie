package org.example.backendagenceimmobilier.service;

import org.example.backendagenceimmobilier.model.ConfigurationAgence;
import org.example.backendagenceimmobilier.repository.ConfigurationAgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceService {

    private final ConfigurationAgenceRepository agenceRepository;

    public Optional<ConfigurationAgence> getConfiguration() {
        return agenceRepository.findAll().stream().findFirst();
    }

    public ConfigurationAgence saveConfiguration(ConfigurationAgence config) {
        return agenceRepository.save(config);
    }
}
