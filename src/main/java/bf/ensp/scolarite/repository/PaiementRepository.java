package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByInscriptionId(Long inscriptionId);
}
