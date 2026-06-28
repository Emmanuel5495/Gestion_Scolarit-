package bf.ensp.scolarite.entity;

import bf.ensp.scolarite.enums.TypeActe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "acte_administratif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActeAdministratif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeActe typeActe;

    @Column(nullable = false)
    private LocalDate dateEmission;

    @Column(nullable = false)
    private String cheminFichier;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
}
