package bf.ensp.scolarite.dto.request;

import bf.ensp.scolarite.enums.StatutDossier;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TraiterDossierRequest {

    @NotNull(message = "La décision est obligatoire")
    private StatutDossier decision;

    // Obligatoire uniquement si decision = REJETE
    private String motifRejet;
}
