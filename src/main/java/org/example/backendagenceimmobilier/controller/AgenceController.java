package org.example.backendagenceimmobilier.controller;


import org.example.backendagenceimmobilier.model.ConfigurationAgence;
import org.example.backendagenceimmobilier.service.AgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agence")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://hajrimmo.tn", "https://www.hajrimmo.tn"})
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping("/info")
    public ResponseEntity<ConfigurationAgence> getAgenceInfo() {
        return agenceService.getConfiguration()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/info")
    public ResponseEntity<ConfigurationAgence> saveAgenceInfo(@RequestBody ConfigurationAgence config) {
        ConfigurationAgence saved = agenceService.saveConfiguration(config);
        return ResponseEntity.ok(saved);
    }
}

