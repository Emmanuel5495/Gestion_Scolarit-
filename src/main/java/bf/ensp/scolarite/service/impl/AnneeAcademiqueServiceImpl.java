package bf.ensp.scolarite.service.impl;

import bf.ensp.scolarite.entity.AnneeAcademique;
import bf.ensp.scolarite.exception.ResourceNotFoundException;
import bf.ensp.scolarite.repository.AnneeAcademiqueRepository;
import bf.ensp.scolarite.service.AnneeAcademiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnneeAcademiqueServiceImpl implements AnneeAcademiqueService {

    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    @Override
    public AnneeAcademique getAnneeActive() {
        return anneeAcademiqueRepository.findByActiveTrue()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aucune année académique active"
                        )
                );
    }

    @Override
    public List<AnneeAcademique> getToutesLesAnnees() {
        return anneeAcademiqueRepository.findAll();
    }

    @Override
    public AnneeAcademique creer(AnneeAcademique annee) {
        return anneeAcademiqueRepository.save(annee);
    }
}
