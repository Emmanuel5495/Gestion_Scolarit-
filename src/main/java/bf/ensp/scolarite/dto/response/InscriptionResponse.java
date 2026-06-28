package bf.ensp.scolarite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionResponse {
    private Long id;
    private LocalDate dateInscription;
    private String matricule;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String anneeAcademique;
}
