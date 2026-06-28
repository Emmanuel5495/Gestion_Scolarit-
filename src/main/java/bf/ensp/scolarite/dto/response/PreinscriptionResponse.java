package bf.ensp.scolarite.dto.response;

import bf.ensp.scolarite.enums.StatutDossier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreinscriptionResponse {
    private Long id;
    private String numeroDossier;
    private LocalDate dateDepot;
    private StatutDossier statut;
    private String motifRejet;
    private String modeEntree;
    private String anneeAcademique;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String emailEtudiant;
    private List<DocumentRequisResponse> documents;
}
