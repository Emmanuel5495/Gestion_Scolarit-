package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.request.PreinscriptionRequest;
import bf.ensp.scolarite.dto.request.TraiterDossierRequest;
import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.dto.response.PreinscriptionResponse;
import bf.ensp.scolarite.service.PreinscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PreinscriptionController {

    private final PreinscriptionService preinscriptionService;

    // Étudiant — soumettre un dossier
    @PostMapping("/api/etudiant/preinscriptions")
    public ResponseEntity<ApiResponse<PreinscriptionResponse>> soumettre(
            @RequestPart("data") @Valid PreinscriptionRequest request,
            @RequestPart(value = "fichiers", required = false)
            List<MultipartFile> fichiers,
            @AuthenticationPrincipal UserDetails userDetails) {
        PreinscriptionResponse response = preinscriptionService.soumettre(
                request, fichiers, userDetails.getUsername()
        );
        return ResponseEntity.ok(
                ApiResponse.success("Dossier soumis avec succès", response)
        );
    }

    // Étudiant — voir mes dossiers
    @GetMapping("/api/etudiant/preinscriptions")
    public ResponseEntity<ApiResponse<List<PreinscriptionResponse>>> mesDossiers(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<PreinscriptionResponse> dossiers = preinscriptionService
                .getMesPreinscriptions(userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("Dossiers récupérés", dossiers)
        );
    }

    // Service pédagogique — voir dossiers en attente
    @GetMapping("/api/service-pedagogique/preinscriptions/en-attente")
    public ResponseEntity<ApiResponse<List<PreinscriptionResponse>>> dossiersEnAttente() {
        List<PreinscriptionResponse> dossiers = preinscriptionService
                .getDossiersEnAttente();
        return ResponseEntity.ok(
                ApiResponse.success("Dossiers en attente récupérés", dossiers)
        );
    }

    // Service pédagogique — voir tous les dossiers
    @GetMapping("/api/service-pedagogique/preinscriptions")
    public ResponseEntity<ApiResponse<List<PreinscriptionResponse>>> tousDossiers() {
        List<PreinscriptionResponse> dossiers = preinscriptionService
                .getTousDossiers();
        return ResponseEntity.ok(
                ApiResponse.success("Tous les dossiers récupérés", dossiers)
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
