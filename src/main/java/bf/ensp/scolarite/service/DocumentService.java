package bf.ensp.scolarite.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    String sauvegarderFichier(MultipartFile fichier, String dossier);
    void supprimerFichier(String chemin);
}
