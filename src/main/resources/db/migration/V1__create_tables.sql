-- Table utilisateur
CREATE TABLE utilisateur (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Table etudiant
CREATE TABLE etudiant (
    id BIGSERIAL PRIMARY KEY,
    matricule VARCHAR(100) UNIQUE,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    sexe VARCHAR(10) NOT NULL,
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(150) NOT NULL,
    utilisateur_id BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_etudiant_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id)
);

-- Table service_pedagogique
CREATE TABLE service_pedagogique (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    utilisateur_id BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_service_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id)
);

-- Table annee_academique
CREATE TABLE annee_academique (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(20) UNIQUE NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    active BOOLEAN DEFAULT FALSE
);

-- Table mode_entree
CREATE TABLE mode_entree (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL UNIQUE
);

-- Table preinscription
CREATE TABLE preinscription (
    id BIGSERIAL PRIMARY KEY,
    numero_dossier VARCHAR(50) UNIQUE,
    date_depot DATE NOT NULL,
    statut VARCHAR(50) NOT NULL,
    motif_rejet TEXT,
    etudiant_id BIGINT NOT NULL,
    mode_entree_id BIGINT NOT NULL,
    annee_academique_id BIGINT NOT NULL,
    CONSTRAINT fk_preinscription_etudiant FOREIGN KEY (etudiant_id) REFERENCES etudiant(id),
    CONSTRAINT fk_preinscription_mode FOREIGN KEY (mode_entree_id) REFERENCES mode_entree(id),
    CONSTRAINT fk_preinscription_annee FOREIGN KEY (annee_academique_id) REFERENCES annee_academique(id)
);

-- Table document_requis
CREATE TABLE document_requis (
    id BIGSERIAL PRIMARY KEY,
    type_document VARCHAR(150) NOT NULL,
    chemin_fichier VARCHAR(255) NOT NULL,
    preinscription_id BIGINT NOT NULL,
    CONSTRAINT fk_document_preinscription FOREIGN KEY (preinscription_id) REFERENCES preinscription(id)
);

-- Table inscription
CREATE TABLE inscription (
    id BIGSERIAL PRIMARY KEY,
    date_inscription DATE NOT NULL,
    etudiant_id BIGINT UNIQUE NOT NULL,
    annee_academique_id BIGINT NOT NULL,
    CONSTRAINT fk_inscription_etudiant FOREIGN KEY (etudiant_id) REFERENCES etudiant(id),
    CONSTRAINT fk_inscription_annee FOREIGN KEY (annee_academique_id) REFERENCES annee_academique(id)
);

-- Table paiement
CREATE TABLE paiement (
    id BIGSERIAL PRIMARY KEY,
    montant_total DECIMAL(10,2) NOT NULL,
    type_paiement VARCHAR(100) NOT NULL,
    date_paiement DATE NOT NULL,
    statut VARCHAR(50) NOT NULL,
    reference_transaction VARCHAR(255),
    inscription_id BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_paiement_inscription FOREIGN KEY (inscription_id) REFERENCES inscription(id)
);

-- Table acte_administratif
CREATE TABLE acte_administratif (
    id BIGSERIAL PRIMARY KEY,
    type_acte VARCHAR(100) NOT NULL,
    date_emission DATE NOT NULL,
    chemin_fichier VARCHAR(255) NOT NULL,
    etudiant_id BIGINT NOT NULL,
    CONSTRAINT fk_acte_etudiant FOREIGN KEY (etudiant_id) REFERENCES etudiant(id)
);
