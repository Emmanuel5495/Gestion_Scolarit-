package bf.ensp.scolarite.dto.response;

import bf.ensp.scolarite.enums.TypeModeEntree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModeEntreeResponse {
    private Long id;
    private String libelle;
    private TypeModeEntree type;
}
