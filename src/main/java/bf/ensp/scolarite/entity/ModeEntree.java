package bf.ensp.scolarite.entity;

import bf.ensp.scolarite.enums.TypeModeEntree;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "mode_entree")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeEntree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TypeModeEntree type;

    @OneToMany(mappedBy = "modeEntree", cascade = CascadeType.ALL)
    private List<Preinscription> preinscriptions;
}
