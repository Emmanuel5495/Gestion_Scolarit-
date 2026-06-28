package bf.ensp.scolarite.controller;

import bf.ensp.scolarite.dto.request.LoginRequest;
import bf.ensp.scolarite.dto.request.RegisterEtudiantRequest;
import bf.ensp.scolarite.dto.response.ApiResponse;
import bf.ensp.scolarite.dto.response.AuthResponse;
import bf.ensp.scolarite.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterEtudiantRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success("Compte créé avec succès", response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.success("Connexion réussie", response)
        );
    }
}
