DROP DATABASE IF EXISTS VegaStudio_DB;

CREATE DATABASE IF NOT EXISTS VegaStudio_DB CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

USE VegaStudio_DB;


CREATE TABLE IF NOT EXISTS joueur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pseudo VARCHAR(255) UNIQUE,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255),
    date_naissance DATE,
    credit FLOAT,
    mot_de_passe_hash VARCHAR(255)
);
