package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByUtilisateurId(Long utilisateurId);
    Optional<Etudiant> findByUtilisateurEmail(String email);
    boolean existsByMatricule(String matricule);
}
