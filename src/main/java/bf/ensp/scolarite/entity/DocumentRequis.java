package bf.ensp.scolarite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_requis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeDocument;

    @Column(nullable = false)
    private String cheminFichier;

    @ManyToOne
    @JoinColumn(name = "preinscription_id", nullable = false)
    private Preinscription preinscription;
}
