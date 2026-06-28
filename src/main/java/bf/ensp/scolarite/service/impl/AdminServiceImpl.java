package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.dto.request.CreateUtilisateurRequest;
import bf.ensp.scolarite.dto.response.EtudiantResponse;
import bf.ensp.scolarite.entity.ServicePedagogique;
import bf.ensp.scolarite.entity.Utilisateur;
import bf.ensp.scolarite.enums.Role;
import bf.ensp.scolarite.repository.EtudiantRepository;
import bf.ensp.scolarite.repository.ServicePedagogiqueRepository;
import bf.ensp.scolarite.repository.UtilisateurRepository;
import bf.ensp.scolarite.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UtilisateurRepository utilisateurRepository;
    private final ServicePedagogiqueRepository servicePedagogiqueRepository;
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ServicePedagogique creerCompteServicePedagogique(
            CreateUtilisateurRequest request) {

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Un compte existe déjà avec cet email"
            );
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .role(Role.SERVICE_PEDAGOGIQUE)
                .build();
        utilisateurRepository.save(utilisateur);

        ServicePedagogique sp = ServicePedagogique.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .utilisateur(utilisateur)
                .build();

        return servicePedagogiqueRepository.save(sp);
    }

    @Override
    public List<EtudiantResponse> getTousLesEtudiants() {
        return etudiantRepository.findAll()
                .stream()
                .map(e -> EtudiantResponse.builder()
                        .id(e.getId())
                        .matricule(e.getMatricule())
                        .nom(e.getNom())
                        .prenom(e.getPrenom())
                        .sexe(e.getSexe())
                        .dateNaissance(e.getDateNaissance())
                        .lieuNaissance(e.getLieuNaissance())
                        .email(e.getUtilisateur().getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}
