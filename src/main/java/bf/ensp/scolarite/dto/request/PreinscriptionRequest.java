package bf.ensp.scolarite.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PreinscriptionRequest {

    // Etape 1 — infos personnelles
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le sexe est obligatoire")
    private String sexe;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    @NotBlank(message = "Le lieu de naissance est obligatoire")
    private String lieuNaissance;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    // Etape 2 — mode d'entrée
    @NotNull(message = "Le mode d'entrée est obligatoire")
    private Long modeEntreeId;

    @NotNull(message = "L'année académique est obligatoire")
    private Long anneeAcademiqueId;
}