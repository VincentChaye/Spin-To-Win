DROP DATABASE IF EXISTS VegaStudio_DB;

CREATE DATABASE IF NOT EXISTS VegaStudio_DB CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

USE VegaStudio_DB;

-- Create 'joueur' table
CREATE TABLE IF NOT EXISTS joueur (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    pseudo VARCHAR(255) UNIQUE,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255),
    date_naissance DATE,
    credit FLOAT,
    mot_de_passe_hash VARCHAR(255)
) ENGINE=InnoDB CHARACTER SET='utf8mb4' COLLATE='utf8mb4_general_ci';

-- Create 'evolutionCredit' table
CREATE TABLE IF NOT EXISTS evolutionCredit(
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    joueur_id BIGINT UNSIGNED NOT NULL,
    credit FLOAT,
    CONSTRAINT fk_evolutionCredit_id FOREIGN KEY (joueur_id) REFERENCES joueur (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET='utf8mb4' COLLATE='utf8mb4_general_ci';

-- Drop the user if it exists
DROP USER IF EXISTS 'Compte_API'@'localhost';

-- Create the user with the specified password
CREATE USER 'Compte_API'@'localhost' IDENTIFIED BY 'FKf1VF6HiRyi1';

-- Grant privileges on both tables
GRANT ALL PRIVILEGES ON VegaStudio_DB.joueur TO 'Compte_API'@'localhost';
GRANT ALL PRIVILEGES ON VegaStudio_DB.evolutionCredit TO 'Compte_API'@'localhost';