package bf.ensp.scolarite.entity;

import bf.ensp.scolarite.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private String typePaiement;

    @Column(nullable = false)
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut;

    private String referenceTransaction;

    @OneToOne
    @JoinColumn(name = "inscription_id", nullable = false)
    private Inscription inscription;
}
