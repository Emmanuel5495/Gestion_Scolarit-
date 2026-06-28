package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.dto.request.PreinscriptionRequest;
import bf.ensp.scolarite.dto.request.TraiterDossierRequest;
import bf.ensp.scolarite.dto.response.DocumentRequisResponse;
import bf.ensp.scolarite.dto.response.PreinscriptionResponse;
import bf.ensp.scolarite.entity.*;
import bf.ensp.scolarite.enums.StatutDossier;
import bf.ensp.scolarite.enums.TypeModeEntree;
import bf.ensp.scolarite.exception.ResourceNotFoundException;
import bf.ensp.scolarite.repository.*;
import bf.ensp.scolarite.service.DocumentService;
import bf.ensp.scolarite.service.MailService;
import bf.ensp.scolarite.service.PreinscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreinscriptionServiceImpl implements PreinscriptionService {

    private final PreinscriptionRepository preinscriptionRepository;
    private final ModeEntreeRepository modeEntreeRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final DocumentRequisRepository documentRequisRepository;
    private final DocumentService documentService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PreinscriptionResponse soumettre(PreinscriptionRequest request,
                                            List<MultipartFile> fichiers) {

        ModeEntree modeEntree = modeEntreeRepository
                .findById(request.getModeEntreeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mode d'entrée non trouvé")
                );

        AnneeAcademique annee = anneeAcademiqueRepository
                .findById(request.getAnneeAcademiqueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Année académique non trouvée")
                );

        Preinscription preinscription = Preinscription.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .sexe(request.getSexe())
                .dateNaissance(request.getDateNaissance())
                .lieuNaissance(request.getLieuNaissance())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .dateDepot(LocalDate.now())
                .statut(StatutDossier.EN_ATTENTE)
                .modeEntree(modeEntree)
                .anneeAcademique(annee)
                .build();

        preinscriptionRepository.save(preinscription);

        if (fichiers != null && !fichiers.isEmpty()) {
            for (MultipartFile fichier : fichiers) {
                String chemin = documentService.sauvegarderFichier(
                        fichier,
                        "preinscription/" + preinscription.getId()
                );
                DocumentRequis doc = DocumentRequis.builder()
                        .typeDocument(fichier.getOriginalFilename())
                        .cheminFichier(chemin)
                        .preinscription(preinscription)
                        .build();
                documentRequisRepository.save(doc);
            }
        }

        return toResponse(preinscription);
    }

    @Override
    public List<PreinscriptionResponse> getDossiersEnAttente() {
        return preinscriptionRepository
                .findByStatut(StatutDossier.EN_ATTENTE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PreinscriptionResponse> getTousDossiers() {
        return preinscriptionRepository.findAllWithDetails()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PreinscriptionResponse getDossierById(Long id) {
        return toResponse(preinscriptionRepository.findByIdWithDetails(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dossier non trouvé")
                ));
    }

    @Override
    @Transactional
    public PreinscriptionResponse traiterDossier(Long id,
                                                 TraiterDossierRequest request) {

        Preinscription preinscription = preinscriptionRepository
                .findByIdWithDetails(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dossier non trouvé")
                );

        if (preinscription.getStatut() != StatutDossier.EN_ATTENTE) {
            throw new IllegalStateException("Ce dossier a déjà été traité");
        }

        switch (request.getDecision()) {
            case VALIDE -> {
                TypeModeEntree type = preinscription.getModeEntree().getType();
                if (type == TypeModeEntree.TRANSFERT
                        || type == TypeModeEntree.PERMUTATION
                        || type == TypeModeEntree.CONVENTION) {
                    preinscription.setStatut(StatutDossier.VALIDE);
                    preinscription.setNumeroDossier(
                            genererNumeroDossier(preinscription.getId())
                    );
                    preinscriptionRepository.save(preinscription);
                    mailService.envoyerNotificationValidation(preinscription);
                } else {
                    preinscription.setStatut(StatutDossier.PRESELECTIONNE);
                    preinscriptionRepository.save(preinscription);
                    mailService.envoyerNotificationConcours(preinscription);
                }
            }
            case REJETE -> {
                if (request.getMotifRejet() == null
                        || request.getMotifRejet().isBlank()) {
                    throw new IllegalArgumentException(
                            "Le motif de rejet est obligatoire"
                    );
                }
                preinscription.setMotifRejet(request.getMotifRejet());
                mailService.envoyerNotificationRejet(preinscription);
                preinscriptionRepository.delete(preinscription);
                return null;
            }
            default -> throw new IllegalArgumentException("Décision invalide");
        }

        return toResponse(preinscription);
    }

    private String genererNumeroDossier(Long id) {
        return "DOS-" + String.format("%04d", id);
    }

    private PreinscriptionResponse toResponse(Preinscription p) {
        List<DocumentRequisResponse> docs = p.getDocuments() == null
                ? List.of()
                : p.getDocuments().stream()
                .map(d -> DocumentRequisResponse.builder()
                        .id(d.getId())
                        .typeDocument(d.getTypeDocument())
                        .cheminFichier(d.getCheminFichier())
                        .build())
                .collect(Collectors.toList());

        return PreinscriptionResponse.builder()
                .id(p.getId())
                .numeroDossier(p.getNumeroDossier())
                .dateDepot(p.getDateDepot())
                .statut(p.getStatut())
                .motifRejet(p.getMotifRejet())
                .modeEntree(p.getModeEntree().getLibelle())
                .anneeAcademique(p.getAnneeAcademique().getLibelle())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .email(p.getEmail())
                .telephone(p.getTelephone())
                .documents(docs)
                .build();
    }
}