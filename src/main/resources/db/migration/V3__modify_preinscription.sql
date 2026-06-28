-- Ajout des infos personnelles du candidat directement dans preinscription
ALTER TABLE preinscription
ADD COLUMN nom VARCHAR(100) NOT NULL DEFAULT '',
ADD COLUMN prenom VARCHAR(100) NOT NULL DEFAULT '',
ADD COLUMN sexe VARCHAR(10) NOT NULL DEFAULT '',
ADD COLUMN date_naissance DATE,
ADD COLUMN lieu_naissance VARCHAR(150),
ADD COLUMN email VARCHAR(255),
ADD COLUMN telephone VARCHAR(20),
ADD COLUMN mot_de_passe VARCHAR(255);

-- Suppression de la contrainte etudiant_id car candidat n'a pas encore de compte
ALTER TABLE preinscription ALTER COLUMN etudiant_id DROP NOT NULL;
