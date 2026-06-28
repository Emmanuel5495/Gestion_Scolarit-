package bf.ensp.scolarite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequisResponse {
    private Long id;
    private String typeDocument;
    private String cheminFichier;
}
