package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public String sauvegarderFichier(MultipartFile fichier, String sousDossier) {
        try {
            Path dossier = Paths.get(uploadDir + sousDossier);
            if (!Files.exists(dossier)) {
                Files.createDirectories(dossier);
            }

            String nomFichier = UUID.randomUUID() + "_" +
                    fichier.getOriginalFilename();
            Path chemin = dossier.resolve(nomFichier);
            fichier.transferTo(chemin);

            return sousDossier + "/" + nomFichier;

        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde du fichier : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier");
        }
    }

    @Override
    public void supprimerFichier(String chemin) {
        try {
            Path path = Paths.get(uploadDir + chemin);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Erreur lors de la suppression du fichier : {}", e.getMessage());
        }
    }
}
