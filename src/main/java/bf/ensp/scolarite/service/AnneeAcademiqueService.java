package bf.ensp.scolarite.service;

import bf.ensp.scolarite.entity.AnneeAcademique;

import java.util.List;

public interface AnneeAcademiqueService {
    AnneeAcademique getAnneeActive();
    List<AnneeAcademique> getToutesLesAnnees();
    AnneeAcademique creer(AnneeAcademique annee);
}
