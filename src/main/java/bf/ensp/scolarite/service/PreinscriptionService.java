package bf.ensp.scolarite.service;

import bf.ensp.scolarite.dto.request.PreinscriptionRequest;
import bf.ensp.scolarite.dto.request.TraiterDossierRequest;
import bf.ensp.scolarite.dto.response.PreinscriptionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PreinscriptionService {
    PreinscriptionResponse soumettre(PreinscriptionRequest request,
                                     List<MultipartFile> fichiers,
                                     String emailEtudiant);
    List<PreinscriptionResponse> getMesPreinscriptions(String emailEtudiant);
    List<PreinscriptionResponse> getDossiersEnAttente();
    List<PreinscriptionResponse> getTousDossiers();
    PreinscriptionResponse getDossierById(Long id);
    PreinscriptionResponse traiterDossier(Long id, TraiterDossierRequest request);
}
