package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.ServicePedagogique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicePedagogiqueRepository extends JpaRepository<ServicePedagogique, Long> {
    Optional<ServicePedagogique> findByUtilisateurId(Long utilisateurId);
}
