package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.request.PreinscriptionRequest;
import bf.ensp.scolarite.dto.request.TraiterDossierRequest;
import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.dto.response.PreinscriptionResponse;
import bf.ensp.scolarite.service.PreinscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PreinscriptionController {

    private final PreinscriptionService preinscriptionService;

    // Public — soumettre un dossier sans compte
    @PostMapping(value = "/api/preinscriptions",
            consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<PreinscriptionResponse>> soumettre(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "fichiers", required = false)
            List<MultipartFile> fichiers) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PreinscriptionRequest request = mapper.readValue(
                dataJson, PreinscriptionRequest.class
        );

        PreinscriptionResponse response = preinscriptionService
                .soumettre(request, fichiers);
        return ResponseEntity.ok(
                ApiResponse.success("Dossier soumis avec succès", response)
        );
    }

    // Service pédagogique — voir dossiers en attente
    @GetMapping("/api/service-pedagogique/preinscriptions/en-attente")
    public ResponseEntity<ApiResponse<List<PreinscriptionResponse>>> dossiersEnAttente() {
        return ResponseEntity.ok(
                ApiResponse.success("Dossiers en attente récupérés",
                        preinscriptionService.getDossiersEnAttente())
        );
    }

    // Service pédagogique — voir tous les dossiers
    @GetMapping("/api/service-pedagogique/preinscriptions")
    public ResponseEntity<ApiResponse<List<PreinscriptionResponse>>> tousDossiers() {
        return ResponseEntity.ok(
                ApiResponse.success("Tous les dossiers récupérés",
                        preinscriptionService.getTousDossiers())
        );
    }

    // Service pédagogique — voir un dossier
    @GetMapping("/api/service-pedagogique/preinscriptions/{id}")
    public ResponseEntity<ApiResponse<PreinscriptionResponse>> dossierById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Dossier récupéré",
                        preinscriptionService.getDossierById(id))
        );
    }

    // Service pédagogique — traiter un dossier
    @PutMapping("/api/service-pedagogique/preinscriptions/{id}/traiter")
    public ResponseEntity<ApiResponse<PreinscriptionResponse>> traiter(
            @PathVariable Long id,
            @Valid @RequestBody TraiterDossierRequest request) {
        PreinscriptionResponse response = preinscriptionService
                .traiterDossier(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Dossier traité avec succès", response)
        );
    }
}