package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.Preinscription;
import bf.ensp.scolarite.enums.StatutDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreinscriptionRepository extends JpaRepository<Preinscription, Long> {
    List<Preinscription> findByStatut(StatutDossier statut);
    List<Preinscription> findByEtudiantUtilisateurEmail(String email);
}
