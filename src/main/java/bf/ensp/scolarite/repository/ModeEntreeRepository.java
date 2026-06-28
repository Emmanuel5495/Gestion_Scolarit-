package bf.ensp.scolarite.repository;

import bf.ensp.scolarite.entity.ModeEntree;
import bf.ensp.scolarite.enums.TypeModeEntree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeEntreeRepository extends JpaRepository<ModeEntree, Long> {
    Optional<ModeEntree> findByType(TypeModeEntree type);
}
