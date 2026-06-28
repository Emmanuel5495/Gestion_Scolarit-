package bf.ensp.scolarite.dto.response;

import bf.ensp.scolarite.enums.StatutDossier;
import lombok.*;

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
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private List<DocumentRequisResponse> documents;
}