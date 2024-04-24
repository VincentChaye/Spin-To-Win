CREATE TABLE IF NOT EXISTS joueur ("
                        + " id SERIAL PRIMARY KEY,"
                        + " pseudo VARCHAR(255) UNIQUE,"
                        + "nom VARCHAR(255),"
                        + "prenom VARCHAR(255)"
                        + "date_naissance DATE,"
                        + "credit FLOAT"
                        + " mot_de_passe_hash VARCHAR(255),"
                        + ")



CREATE TABLE IF NOT EXISTS joueur (
    id SERIAL,
    pseudo VARCHAR(255) UNIQUE,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    date_naissance DATE,
    credit BIGINT,
    mot_de_passe_hash VARCHAR(255)
);
