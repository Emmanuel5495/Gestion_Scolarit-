package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.Preinscription;
import bf.ensp.scolarite.enums.StatutDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreinscriptionRepository extends JpaRepository<Preinscription, Long> {

    @Query("SELECT p FROM Preinscription p " +
            "LEFT JOIN FETCH p.documents " +
            "LEFT JOIN FETCH p.modeEntree " +
            "LEFT JOIN FETCH p.anneeAcademique " +
            "WHERE p.statut = :statut")
    List<Preinscription> findByStatut(StatutDossier statut);

    @Query("SELECT p FROM Preinscription p " +
            "LEFT JOIN FETCH p.documents " +
            "LEFT JOIN FETCH p.modeEntree " +
            "LEFT JOIN FETCH p.anneeAcademique")
    List<Preinscription> findAllWithDetails();

    @Query("SELECT p FROM Preinscription p " +
            "LEFT JOIN FETCH p.documents " +
            "LEFT JOIN FETCH p.modeEntree " +
            "LEFT JOIN FETCH p.anneeAcademique " +
            "WHERE p.id = :id")
    Optional<Preinscription> findByIdWithDetails(Long id);
}