package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.dto.request.LoginRequest;
import bf.ensp.scolarite.entity.ServicePedagogique;
import bf.ensp.scolarite.repository.ServicePedagogiqueRepository;
import bf.ensp.scolarite.dto.request.RegisterEtudiantRequest;
import bf.ensp.scolarite.dto.response.AuthResponse;
import bf.ensp.scolarite.entity.Etudiant;
import bf.ensp.scolarite.entity.ServicePedagogique;
import bf.ensp.scolarite.entity.Utilisateur;
import bf.ensp.scolarite.enums.Role;
import bf.ensp.scolarite.exception.ResourceNotFoundException;
import bf.ensp.scolarite.repository.EtudiantRepository;
import bf.ensp.scolarite.repository.UtilisateurRepository;
import bf.ensp.scolarite.security.JwtUtils;
import bf.ensp.scolarite.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ServicePedagogiqueRepository servicePedagogiqueRepository;

    @Override
    @Transactional
    public AuthResponse register(RegisterEtudiantRequest request) {

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Un compte existe déjà avec cet email"
            );
        }

        // Création du compte utilisateur
        Utilisateur utilisateur = Utilisateur.builder()
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .role(Role.ETUDIANT)
                .build();
        utilisateurRepository.save(utilisateur);

        // Création du profil étudiant
        Etudiant etudiant = Etudiant.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .sexe(request.getSexe())
                .dateNaissance(request.getDateNaissance())
                .lieuNaissance(request.getLieuNaissance())
                .utilisateur(utilisateur)
                .build();
        etudiantRepository.save(etudiant);

        // Génération du token JWT
        String token = jwtUtils.generateToken(utilisateur);

        return AuthResponse.builder()
                .token(token)
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .nom(etudiant.getNom())
                .prenom(etudiant.getPrenom())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // Spring Security vérifie email + mot de passe
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Utilisateur non trouvé")
                );

        String token = jwtUtils.generateToken(utilisateur);

        // Récupère nom et prénom selon le rôle
        String nom = "";
        String prenom = "";

        switch (utilisateur.getRole()) {
            case ETUDIANT -> {
                Etudiant etudiant = etudiantRepository
                        .findByUtilisateurId(utilisateur.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Profil étudiant non trouvé")
                        );
                nom = etudiant.getNom();
                prenom = etudiant.getPrenom();
            }
            case SERVICE_PEDAGOGIQUE -> {
                ServicePedagogique sp = servicePedagogiqueRepository
                        .findByUtilisateurId(utilisateur.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Profil service pédagogique non trouvé")
                        );
                nom = sp.getNom();
                prenom = sp.getPrenom();
            }
            case ADMIN -> {
                nom = "Administrateur";
                prenom = "";
            }
        }

        return AuthResponse.builder()
                .token(token)
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .nom(nom)
                .prenom(prenom)
                .build();
    }
}
