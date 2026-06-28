package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    Optional<Inscription> findByEtudiantId(Long etudiantId);
}
