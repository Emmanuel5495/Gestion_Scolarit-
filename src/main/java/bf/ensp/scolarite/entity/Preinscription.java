package bf.ensp.scolarite.entity;

import bf.ensp.scolarite.enums.StatutDossier;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "preinscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preinscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Infos personnelles du candidat
    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String sexe;

    @Column
    private LocalDate dateNaissance;

    @Column
    private String lieuNaissance;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private String motDePasse;

    // Infos dossier
    @Column(unique = true)
    private String numeroDossier;

    @Column(nullable = false)
    private LocalDate dateDepot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDossier statut;

    private String motifRejet;

    @ManyToOne
    @JoinColumn(name = "mode_entree_id", nullable = false)
    private ModeEntree modeEntree;

    @ManyToOne
    @JoinColumn(name = "annee_academique_id", nullable = false)
    private AnneeAcademique anneeAcademique;

    @OneToMany(mappedBy = "preinscription", cascade = CascadeType.ALL)
    private List<DocumentRequis> documents;
}