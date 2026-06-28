package bf.ensp.scolarite.dto.response;

import bf.ensp.scolarite.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementResponse {
    private Long id;
    private BigDecimal montantTotal;
    private String typePaiement;
    private LocalDate datePaiement;
    private StatutPaiement statut;
    private String referenceTransaction;
}
