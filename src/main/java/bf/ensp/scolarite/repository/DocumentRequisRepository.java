package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.DocumentRequis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRequisRepository extends JpaRepository<DocumentRequis, Long> {
    List<DocumentRequis> findByPreinscriptionId(Long preinscriptionId);
}
