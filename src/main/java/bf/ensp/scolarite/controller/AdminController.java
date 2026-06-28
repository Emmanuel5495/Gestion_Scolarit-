package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.request.CreateUtilisateurRequest;
import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.dto.response.EtudiantResponse;
import bf.ensp.scolarite.entity.ServicePedagogique;
import bf.ensp.scolarite.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/users/service-pedagogique")
    public ResponseEntity<ApiResponse<ServicePedagogique>> creerCompte(
            @Valid @RequestBody CreateUtilisateurRequest request) {
        ServicePedagogique sp = adminService
                .creerCompteServicePedagogique(request);
        return ResponseEntity.ok(
                ApiResponse.success("Compte créé avec succès", sp)
        );
    }

    @GetMapping("/etudiants")
    public ResponseEntity<ApiResponse<List<EtudiantResponse>>> tousLesEtudiants() {
        return ResponseEntity.ok(
                ApiResponse.success("Étudiants récupérés",
                        adminService.getTousLesEtudiants())
        );
    }
}
