package bf.ensp.scolarite.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreinscriptionRequest {

    @NotNull(message = "Le mode d'entrée est obligatoire")
    private Long modeEntreeId;

    @NotNull(message = "L'année académique est obligatoire")
    private Long anneeAcademiqueId;
}
