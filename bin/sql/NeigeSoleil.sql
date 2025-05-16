    DROP DATABASE IF EXISTS NeigeSoleil;
    CREATE DATABASE IF NOT EXISTS NeigeSoleil;
    USE NeigeSoleil;


    # ---------------------------------------------------------------------------
    # TABLE : VILLE 
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS VILLE (
        ID_VILLE INT NOT NULL AUTO_INCREMENT,
        NOM VARCHAR(128) DEFAULT NULL,
        CONSTRAINT PK_VILLE PRIMARY KEY (ID_VILLE)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : STATIONS (dépend de REGION)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS STATIONS (
        ID_STATION INT NOT NULL AUTO_INCREMENT,
        ID_VILLE INT NOT NULL,
        TEL_MAIRIE VARCHAR(15) NOT NULL,
        HABITANTS INT DEFAULT NULL,
        TOURISTES INT DEFAULT NULL,
        SKIEURS INT DEFAULT NULL,
        CONSTRAINT PK_STATIONS PRIMARY KEY (ID_STATION),
        CONSTRAINT FK_STATIONS_VILLE FOREIGN KEY (ID_VILLE) REFERENCES VILLE(ID_VILLE)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : Activités (dépend de STATIONS)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS ACTIVITES (
        ID_ACTIVITE INT NOT NULL AUTO_INCREMENT,
        ID_STATION INT NOT NULL,
        NOM VARCHAR(20) NOT NULL,
        TARIF DECIMAL(6,2) DEFAULT NULL,
        NATURE TEXT DEFAULT NULL,
        CONSTRAINT PK_ACTIVITES PRIMARY KEY (ID_ACTIVITE),
        CONSTRAINT FK_ACTIVITES_STATIONS FOREIGN KEY (ID_STATION) REFERENCES STATIONS(ID_STATION)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : BATIMENT (dépend de VILLE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS BATIMENT (
        ID_BATIMENT VARCHAR(30) NOT NULL,
        ID_VILLE INT NOT NULL,
        NUMERO INT NOT NULL, 
        ADRESSE VARCHAR(60) NOT NULL, 
        CODE_POSTAL INT(5), 
        CONSTRAINT PK_BATIMENT PRIMARY KEY (ID_BATIMENT),
        CONSTRAINT FK_BATIMENT_VILLE FOREIGN KEY (ID_VILLE) REFERENCES VILLE(ID_VILLE)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : PROPRIETAIRE (dépend de VILLE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS PROPRIETAIRE (
        ID_PROPRIETAIRE INT NOT NULL AUTO_INCREMENT,
        ID_VILLE INT NOT NULL,
        
        NOM VARCHAR(40) NOT NULL,

        PRENOM VARCHAR(40) NOT NULL,

        ADRESSE VARCHAR(70) NOT NULL,

        CODE_POSTAL int(5) NOT NULL,

        TELEPHONE VARCHAR(10) NOT NULL,

        EMAIL VARCHAR(50) NOT NULL,

        MDP VARCHAR(255) NOT NULL,   

        ROLE enum ('Admin','Locataire','Proprietaire'),

        Constraint Unicite_email Unique (email),
        CONSTRAINT PK_PROPRIETAIRE PRIMARY KEY (ID_PROPRIETAIRE)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : APPARTEMENT (dépend de BATIMENT, VILLE, PROPRIETAIRE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS APPARTEMENT (
        ID_BATIMENT VARCHAR(30) NOT NULL,
        NUMERO_APPARTEMENT INT NOT NULL,
        ID_VILLE INT NOT NULL,
        ID_PROPRIETAIRE INT NOT NULL,
        ADRESSE VARCHAR(70) NOT NULL,
        CODE_POSTAL INT NOT NULL,
        IMG1 mediumblob DEFAULT NULL,
        IMG2 mediumblob DEFAULT NULL,
        IMG3 mediumblob DEFAULT NULL,
        IMG4 mediumblob DEFAULT NULL,
        TYPE ENUM('F1', 'F2', 'F3', 'F4', 'F5') NOT NULL,
        EXPOSITION ENUM('Nord', 'Nord-est','Nord-ouest','Sud','Sud-est','Sud-ouest','Est', 'Ouest') NOT NULL,
        SURFACE_HABITABLE INT NOT NULL,
        SURFACE_BALCON INT DEFAULT NULL,
        CAPACITE INT NOT NULL,
        DISTANCE INT NOT NULL,
        CONSTRAINT PK_APPARTEMENT PRIMARY KEY (ID_BATIMENT, NUMERO_APPARTEMENT),
        CONSTRAINT FK_APPARTEMENT_VILLE FOREIGN KEY (ID_VILLE) REFERENCES VILLE(ID_VILLE) on update cascade,
        CONSTRAINT FK_APPARTEMENT_PROPRIETAIRE FOREIGN KEY (ID_PROPRIETAIRE) REFERENCES PROPRIETAIRE(ID_PROPRIETAIRE) on update cascade on delete cascade,
        CONSTRAINT FK_APPARTEMENT_BATIMENT FOREIGN KEY (ID_BATIMENT) REFERENCES BATIMENT(ID_BATIMENT) on update cascade on delete cascade
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : CONTRAT_DE_MANDAT_LOCATIF (dépend de APPARTEMENT, PROPRIETAIRE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS CONTRAT_DE_MANDAT_LOCATIF (
        ID_CONTRAT INT NOT NULL AUTO_INCREMENT,
        ID_PROPRIETAIRE INT NOT NULL,
        ID_BATIMENT VARCHAR(30) NOT NULL,
        NUMERO_APPARTEMENT INT NOT NULL,
        RIB VARCHAR(34) NOT NULL,
        TARIF_HAUTE DECIMAL(7,2) NOT NULL,
        TARIF_MOYEN DECIMAL(7,2) NOT NULL,
        TARIF_BASSE DECIMAL(7,2) NOT NULL,
        DATE_INACTIF date default null,
        ANNEE_DE_LOCATION YEAR NOT NULL,
        ETAT ENUM('Actif', 'Inactif') NOT NULL,
        CONSTRAINT PK_CONTRAT PRIMARY KEY (ID_CONTRAT),
        CONSTRAINT FK_CONTRAT_PROPRIETAIRE FOREIGN KEY (ID_PROPRIETAIRE) REFERENCES PROPRIETAIRE(ID_PROPRIETAIRE),
        CONSTRAINT FK_CONTRAT_APPARTEMENT FOREIGN KEY (ID_BATIMENT, NUMERO_APPARTEMENT) REFERENCES APPARTEMENT(ID_BATIMENT, NUMERO_APPARTEMENT)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : EXCLUSIONS (dépend de CONTRAT_DE_MANDAT_LOCATIF)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS EXCLUSIONS (
        ID_EXCLUSIONS INT NOT NULL AUTO_INCREMENT,
        ID_CONTRAT INT NOT NULL,
        DATE_DEBUT DATE NOT NULL,
        DATE_FIN DATE NOT NULL,
        CONSTRAINT PK_EXCLUSIONS PRIMARY KEY (ID_EXCLUSIONS),
        CONSTRAINT FK_EXCLUSIONS_CONTRAT FOREIGN KEY (ID_CONTRAT) REFERENCES CONTRAT_DE_MANDAT_LOCATIF(ID_CONTRAT)on delete cascade on update cascade 
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : EQUIPEMENTS (dépend de APPARTEMENT)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS EQUIPEMENTS (
        NUMERO_SERIE VARCHAR(128) NOT NULL,
        ID_BATIMENT VARCHAR(30) NOT NULL,
        NUMERO_APPARTEMENT INT NOT NULL,
        TYPE_EQUIPEMENT VARCHAR(40) NOT NULL,
        DATE_ACHAT DATE NOT NULL,
        CARACTERISTIQUES TEXT NOT NULL,
        CONSTRAINT PK_EQUIPEMENTS PRIMARY KEY (NUMERO_SERIE),
        CONSTRAINT FK_EQUIPEMENT_APPARTEMENT FOREIGN KEY (ID_BATIMENT, NUMERO_APPARTEMENT) REFERENCES APPARTEMENT(ID_BATIMENT, NUMERO_APPARTEMENT)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : LOCATAIRE (dépend de VILLE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS LOCATAIRE (
        ID_LOCATAIRE INT NOT NULL,
        ID_VILLE INT NOT NULL,

        NOM VARCHAR(40) NOT NULL,

        PRENOM VARCHAR(40) NOT NULL,

        ADRESSE VARCHAR(70) NOT NULL,

        CODE_POSTAL int(5) NOT NULL,

        TELEPHONE VARCHAR(10) NOT NULL,

        EMAIL VARCHAR(50) NOT NULL,

        MDP VARCHAR(255) NOT NULL,   

        ROLE enum ('Admin','Locataire','Proprietaire'),

        CONSTRAINT PK_LOCATAIRE PRIMARY KEY (ID_LOCATAIRE),
        Constraint Unicite_email Unique (email)
    ) ENGINE=InnoDB;



    # ---------------------------------------------------------------------------
    # TABLE : CONTRAT_DE_RESERVATION (dépend de LOCATAIRE, APPARTEMENT)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS CONTRAT_DE_RESERVATION (
        NUMERO_CONTRAT INT NOT NULL AUTO_INCREMENT,
        ID_LOCATAIRE INT NOT NULL,
        ID_BATIMENT VARCHAR(30) NOT NULL,
        NUMERO_APPARTEMENT INT NOT NULL,
        DATE_RESERVATION DATE NOT NULL,
        ARRHES ENUM('Payé', 'Non_Payé') NOT NULL,
        SOLDE ENUM('Payé', 'Non_Payé') NOT NULL,
        ETAT ENUM('Confirmé','En_Cours', 'Annulé') NOT NULL, 
        CONSTRAINT PK_RESERVATION PRIMARY KEY (NUMERO_CONTRAT),
        CONSTRAINT FK_RESERVATION_LOCATAIRE FOREIGN KEY (ID_LOCATAIRE) REFERENCES LOCATAIRE(ID_LOCATAIRE),
        CONSTRAINT FK_RESERVATION_APPARTEMENT FOREIGN KEY (ID_BATIMENT, NUMERO_APPARTEMENT) REFERENCES APPARTEMENT(ID_BATIMENT, NUMERO_APPARTEMENT)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------
    # TABLE : SEMAINES_LOUEES (Depend de contrat_de_reservation)    
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS SEMAINES_LOUEES (
        ID_SEMAINE INT NOT NULL AUTO_INCREMENT,
        NUMERO_CONTRAT INT NOT NULL, 
        DATE_DEBUT DATE NOT NULL,
        DATE_FIN DATE NOT NULL,
        CONSTRAINT PK_SEMAINES PRIMARY KEY (ID_SEMAINE),
        CONSTRAINT FK_SEMAINES_CONTRAT_DE_RESERVATION FOREIGN KEY (NUMERO_CONTRAT) REFERENCES CONTRAT_DE_RESERVATION(NUMERO_CONTRAT) on delete cascade on update cascade
    ) ENGINE=InnoDB;


    # ---------------------------------------------------------------------------
    # TABLE : LOCATIONS (dépend de APPARTEMENT, CONTRAT_DE_RESERVATION, LOCATAIRE)
    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS LOCATIONS (
        ID_BATIMENT VARCHAR(30) NOT NULL,
        NUMERO_APPARTEMENT INT NOT NULL,
        NUMERO_CONTRAT INT NOT NULL,
        ID_LOCATAIRE INT NOT NULL,
        CONSTRAINT PK_LOCATIONS PRIMARY KEY (ID_BATIMENT, NUMERO_APPARTEMENT, NUMERO_CONTRAT, ID_LOCATAIRE),
        CONSTRAINT FK_LOCATIONS_APPARTEMENT FOREIGN KEY (ID_BATIMENT, NUMERO_APPARTEMENT) REFERENCES APPARTEMENT(ID_BATIMENT, NUMERO_APPARTEMENT),
        CONSTRAINT FK_LOCATIONS_CONTRAT FOREIGN KEY (NUMERO_CONTRAT) REFERENCES CONTRAT_DE_RESERVATION(NUMERO_CONTRAT),
        CONSTRAINT FK_LOCATIONS_LOCATAIRE FOREIGN KEY (ID_LOCATAIRE) REFERENCES LOCATAIRE(ID_LOCATAIRE)
    ) ENGINE=InnoDB;

    # ---------------------------------------------------------------------------

    # TABLE : USER

    # ---------------------------------------------------------------------------
    CREATE TABLE IF NOT EXISTS USER (

        ID_USER INT AUTO_INCREMENT,

        ID_VILLE INT NOT NULL,

        NOM VARCHAR(40) NOT NULL,

        PRENOM VARCHAR(40) NOT NULL,

        ADRESSE VARCHAR(70) NOT NULL,

        CODE_POSTAL int(5) NOT NULL,

        TELEPHONE VARCHAR(10) NOT NULL,

        EMAIL VARCHAR(50) NOT NULL,

        MDP VARCHAR(255) NOT NULL,   

        ROLE enum ('Admin','Locataire','Proprietaire'),

        CONSTRAINT PK_USER PRIMARY KEY (ID_USER),
        Constraint FK_User_Ville FOREIGN KEY (ID_VILLE) REFERENCES VILLE(ID_VILLE),
        Constraint Unicite_email Unique (email)

        );

    # ---------------------------------------------------------------------------

    # TABLE : Grain de sel
    # ---------------------------------------------------------------------------

    create table if not exists Saliere(
    Grain_de_sel varchar(255) not null); 

    # ---------------------------------------------------------------------------
    # TABLE : password_resets
    # ---------------------------------------------------------------------------
    CREATE TABLE `password_resets` (
    `ID_USER` INT NOT NULL,
    `token` VARCHAR(64) NOT NULL,
    `expiration` DATETIME NOT NULL,
    PRIMARY KEY (`ID_USER`),
    UNIQUE KEY `token` (`token`)
    );




    ------------------------------
    # Triggers
    ------------------------------
    # Trigger pour Gerer l''héritage User
    DROP TRIGGER IF EXISTS insert_Locataire;
    DELIMITER //
    CREATE TRIGGER insert_Locataire
    BEFORE INSERT ON Locataire
    FOR EACH ROW
    BEGIN
    if new.ID_LOCATAIRE is null or new.ID_LOCATAIRE in (select ID_USER from USER) or new.ID_LOCATAIRE = 0
        then
            set new.ID_LOCATAIRE = ifnull((select ID_USER from USER where ID_USER >= all(select ID_USER from USER)), 0) + 1;
    end if;
    INSERT INTO USER (ID_USER, ID_VILLE, NOM, PRENOM, ADRESSE, CODE_POSTAL, TELEPHONE, EMAIL, MDP, ROLE)
    VALUES (new.ID_LOCATAIRE, new.ID_VILLE, new.NOM, new.PRENOM, new.ADRESSE, new.CODE_POSTAL, new.TELEPHONE, new.EMAIL, new.MDP, 'Locataire');
    END //
    DELIMITER ;

    Drop trigger if exists Update_Locataire; 
    DELIMITER //
    Create trigger Update_Locataire
    Before update on LOCATAIRE
    for EACH ROW
    Begin 
    update user set NOM = new.NOM, PRENOM = new.PRENOM, ADRESSE = new.ADRESSE, CODE_POSTAL = new.CODE_POSTAL, TELEPHONE = new.TELEPHONE, EMAIL = new.EMAIL, MDP = new.MDP where ID_USER = new.ID_LOCATAIRE;
    end//
    DELIMITER ;

    # Trigger pour Gerer l''héritage User
    DROP TRIGGER IF EXISTS insert_Proprietaire;
    DELIMITER //
    CREATE TRIGGER insert_Proprietaire
    BEFORE INSERT ON PROPRIETAIRE
    FOR EACH ROW
    BEGIN
    IF NEW.ID_PROPRIETAIRE IS NULL OR NEW.ID_PROPRIETAIRE IN (SELECT ID_USER FROM USER) OR NEW.ID_PROPRIETAIRE = 0 THEN
        SET NEW.ID_PROPRIETAIRE = IFNULL((SELECT ID_USER FROM USER WHERE ID_USER >= ALL(SELECT ID_USER FROM USER)), 0) + 1;
    END IF;
    INSERT INTO USER (ID_USER, ID_VILLE, NOM, PRENOM, ADRESSE, CODE_POSTAL, TELEPHONE, EMAIL, MDP, ROLE)
    VALUES (NEW.ID_PROPRIETAIRE, NEW.ID_VILLE, NEW.NOM, NEW.PRENOM, NEW.ADRESSE, NEW.CODE_POSTAL, NEW.TELEPHONE, NEW.EMAIL, NEW.MDP, 'Proprietaire');
    END //
    DELIMITER ;

    Drop trigger if exists Update_Proprietaire; 
    DELIMITER //
    Create trigger Update_Proprietaire
    After update on PROPRIETAIRE
    for EACH ROW
    Begin 
    update USER set NOM = new.NOM, PRENOM = new.PRENOM, ADRESSE = new.ADRESSE, CODE_POSTAL = new.CODE_POSTAL, TELEPHONE = new.TELEPHONE, EMAIL = new.EMAIL, MDP = new.MDP where ID_USER = new.ID_PROPRIETAIRE;
    end//
    DELIMITER ;



    # TRIGGER Pour creer une Location lors du paiement du solde d''un contrat de reservation (Update)
    DROP TRIGGER IF EXISTS Creation_Location_Update;
    DELIMITER //
    CREATE TRIGGER Creation_Location_Update
    AFTER UPDATE ON contrat_de_reservation
    FOR EACH ROW
    BEGIN
        IF NEW.SOLDE = 'Payé' THEN 
            INSERT INTO LOCATIONS (ID_BATIMENT, NUMERO_APPARTEMENT, NUMERO_CONTRAT, ID_LOCATAIRE)
            VALUES (NEW.ID_BATIMENT, NEW.NUMERO_APPARTEMENT, NEW.NUMERO_CONTRAT, NEW.ID_LOCATAIRE);
        END IF;
    END //
    DELIMITER ;

    # TRIGGER Pour creer une Location lors du paiement du solde d''un contrat de reservation (Insert)
    DROP TRIGGER IF EXISTS Creation_Location_Insert;
    DELIMITER //
    CREATE TRIGGER Creation_Location_Insert
    AFTER INSERT ON contrat_de_reservation
    FOR EACH ROW
    BEGIN
        IF NEW.SOLDE = 'Payé' THEN
            INSERT INTO LOCATIONS (ID_BATIMENT, NUMERO_APPARTEMENT, NUMERO_CONTRAT, ID_LOCATAIRE)
            VALUES (NEW.ID_BATIMENT, NEW.NUMERO_APPARTEMENT, NEW.NUMERO_CONTRAT, NEW.ID_LOCATAIRE);
        END IF;
    END //
    DELIMITER ;


    # Trigger pour inscrire la date d''inactivité 
    DELIMITER //

    DROP TRIGGER if EXISTS trigger_mise_a_jour_date_inactif;
    CREATE TRIGGER trigger_mise_a_jour_date_inactif
    BEFORE UPDATE ON CONTRAT_DE_MANDAT_LOCATIF
    FOR EACH ROW
    BEGIN
        IF NEW.ETAT = 'Inactif' AND OLD.ETAT = 'Actif' THEN
            SET NEW.DATE_INACTIF = CURDATE(); 
        END IF;
    END;
    //

    DELIMITER ;

    # Trigger pour mettre à jour le mot de passe avec le grain de sel

    DELIMITER $$

    DROP TRIGGER IF EXISTS Sel_Locataire$$
    CREATE TRIGGER Sel_Locataire
    BEFORE INSERT ON Locataire
    FOR EACH ROW
    BEGIN
    DECLARE sel VARCHAR(255);

    -- Récupère le grain de sel depuis la table configuration
    SELECT Grain_de_sel
    INTO sel
    FROM Saliere; 

    -- Concatène le SHA1 du mot de passe et le SHA1 du grain de sel
    SET NEW.mdp = CONCAT(SHA1(NEW.mdp), SHA1(sel));
    END$$

    DELIMITER ;

    DELIMITER $$

    DROP TRIGGER IF EXISTS Sel_Proprietaire$$
    CREATE TRIGGER Sel_Proprietaire
    BEFORE INSERT ON Proprietaire
    FOR EACH ROW
    BEGIN
    DECLARE sel VARCHAR(255);

    -- Récupère le grain de sel depuis la table configuration
    SELECT Grain_de_sel
    INTO sel
    FROM Saliere; 

    -- Concatène le SHA1 du mot de passe et le SHA1 du grain de sel
    SET NEW.mdp = CONCAT(SHA1(NEW.mdp), SHA1(sel));
    END$$

    DELIMITER ;

    DELIMITER $$

    DROP TRIGGER IF EXISTS Sel_User$$
    CREATE TRIGGER Sel_User
    BEFORE INSERT ON User
    FOR EACH ROW
    BEGIN
    DECLARE sel VARCHAR(255);

    -- Récupère le grain de sel depuis la table configuration
    SELECT Grain_de_sel
    INTO sel
    FROM Saliere; 

    -- Concatène le SHA1 du mot de passe et le SHA1 du grain de sel
    SET NEW.mdp = CONCAT(SHA1(NEW.mdp), SHA1(sel));
    END$$

    DELIMITER ;


    ------------------------------
    # Événements
    ------------------------------

    # Événement (ou tâche) qui inspecte tous les jours si un contrat est inactif depuis 5 ans 
    DELIMITER $

    SET GLOBAL event_scheduler = ON;

    DROP EVENT if EXISTS supprimer_contrats_inactifs;
    CREATE EVENT supprimer_contrats_inactifs
    ON SCHEDULE EVERY 1 DAY -- S'exécute tous les jours (modifiable selon le besoin)
    DO
    BEGIN
        DELETE FROM CONTRAT_DE_MANDAT_LOCATIF
        WHERE ETAT = 'Inactif'
        AND YEAR(CURDATE()) - YEAR(DATE_INACTIF) >= 5;
    END$

    DELIMITER ;

    ------------------------------
    # Procédures Stockées 
    ------------------------------

    -- Procédure pour vérifier si un appartement existe
        DELIMITER //

        CREATE PROCEDURE VerifierAppartement(
            IN p_id_batiment VARCHAR(10),
            IN p_numero_appartement INT,
            OUT existe TINYINT(1) -- Paramètre de sortie pour renvoyer true (1) ou false (0)
        )
        BEGIN
            DECLARE compteur INT;

            -- Vérifier si l'appartement existe dans la table
            SELECT COUNT(*) INTO compteur 
            FROM appartement 
            WHERE ID_BATIMENT = p_id_batiment 
            AND NUMERO_APPARTEMENT = p_numero_appartement;

            -- Si v_count > 0, alors l'appartement existe, donc on met o_ok à 1, sinon à 0
            SET existe = (compteur > 0);
        END //

        DELIMITER ;

    -- Procedure pour verifier si une reservation est passée en location (Afficher les boutons Modifier/Supprimer ou pas dans la page de reservation)

    DELIMITER //

    Create procedure verifierLocation(
        IN p_numero_contrat INT
    )

    BEGIN
        DECLARE compteur INT;

        SELECT COUNT(*) INTO compteur
        FROM LOCATIONS
        WHERE NUMERO_CONTRAT = p_numero_contrat;

        SET @existe = (compteur > 0);
        SELECT @existe as existe; 
    END //

    DELIMITER ; 

    -- Insertion dans la table VILLE
    INSERT INTO VILLE (NOM) VALUES
    ('Paris'),
    ('Lyon'),
    ('Marseille');

    --Grain de sel
    INSERT INTO Saliere (Grain_de_sel) VALUES (sha1('2456878090'));

    -- Insertion dans la table USER pour l'admin
    INSERT INTO USER (ID_VILLE, NOM, PRENOM, ADRESSE, CODE_POSTAL, TELEPHONE, EMAIL, MDP, ROLE) VALUES
    (1, 'Dupont', 'Jean', '123 Rue de Paris', 75001, '0102030405', 'A@A.com', ('123'), 'Admin');

    -- Insertion dans la table LOCATAIRE
    INSERT INTO LOCATAIRE (ID_LOCATAIRE, ID_VILLE, NOM, PRENOM, ADRESSE, CODE_POSTAL, TELEPHONE, EMAIL, MDP, ROLE) VALUES
    (2, 2, 'Martin', 'Sophie', '456 Avenue de Lyon', 69002, '0607080910', 'L@L.com', '456', 'Locataire');

    -- Insertion dans la table PROPRIETAIRE
    INSERT INTO PROPRIETAIRE (ID_PROPRIETAIRE, ID_VILLE, NOM, PRENOM, ADRESSE, CODE_POSTAL, TELEPHONE, EMAIL, MDP, ROLE) VALUES
    (3, 3, 'Durand', 'Pierre', '789 Boulevard de Marseille', 13003, '0203040506', 'P@P.com', ('789'), 'Proprietaire');

    -- Insertion dans la table STATIONS
    INSERT INTO STATIONS (ID_VILLE, TEL_MAIRIE, HABITANTS, TOURISTES, SKIEURS) VALUES
    (1, '0102030405', 1000000, 500000, 200000),
    (2, '0607080910', 500000, 300000, 100000),
    (3, '0203040506', 800000, 400000, 150000);

    -- Insertion dans la table ACTIVITES
    INSERT INTO ACTIVITES (ID_STATION, NOM, TARIF, NATURE) VALUES
    (1, 'Ski', 50.00, 'Sport d\'hiver'),
    (2, 'Snowboard', 60.00, 'Sport d\'hiver'),
    (3, 'Randonnée', 30.00, 'Activité de plein air');

    -- Insertion dans la table BATIMENT
    INSERT INTO BATIMENT (ID_BATIMENT, ID_VILLE, NUMERO, ADRESSE, CODE_POSTAL) VALUES
    ('B001', 1, 1, '123 Rue de Paris', 75001),
    ('B002', 2, 2, '456 Avenue de Lyon', 69002),
    ('B003', 3, 3, '789 Boulevard de Marseille', 13003);


    INSERT INTO APPARTEMENT (ID_BATIMENT, NUMERO_APPARTEMENT, ID_VILLE, ID_PROPRIETAIRE, ADRESSE, CODE_POSTAL, IMG1, IMG2, IMG3, TYPE, EXPOSITION, SURFACE_HABITABLE, SURFACE_BALCON, CAPACITE, DISTANCE)
    VALUES
    ('B001', 101, 1, 3, '10 rue de la Paix', 75001, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/neige.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F2', 'Sud', 45, 5, 2, 500),
    ('B002', 102, 2, 3, '20 avenue Victor Hugo', 75016, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F3', 'Ouest', 60, 7, 3, 600),
    ('B003', 103, 3, 3, '5 boulevard Haussmann', 75009, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F4', 'Est', 80, 10, 4, 700),
    ('B001', 104, 1, 3, '15 rue Lafayette', 75010, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F1', 'Nord', 30, NULL, 1, 400),
    ('B002', 105, 2, 3, '25 rue des Champs', 75008, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F5', 'Sud-est', 100, 12, 5, 800),
    ('B003', 106, 3, 3, '30 rue Montmartre', 75002, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F2', 'Nord-ouest', 50, 6, 2, 550),
    ('B001', 107, 1, 3, '12 rue du Louvre', 75001, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F3', 'Sud-ouest', 65, 8, 3, 650),
    ('B002', 108, 2, 3, '18 rue de Rivoli', 75004, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F4', 'Nord-est', 75, 9, 4, 750),
    ('B003', 109, 3, 3, '22 rue Saint-Honoré', 75001, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F1', 'Est', 35, NULL, 1, 450),
    ('B001', 110, 1, 3, '28 rue des Petits Champs', 75002, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F5', 'Ouest', 110, 15, 5, 900),
    ('B002', 111, 2, 3, '32 rue Oberkampf', 75011, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F2', 'Sud', 48, 5, 2, 520),
    ('B003', 112, 3, 3, '38 rue de Belleville', 75020, LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), LOAD_FILE('C:/wamp64/tmp/appart.jpg'), 'F4', 'Sud-ouest', 70, 8, 3, 600);

    INSERT INTO CONTRAT_DE_MANDAT_LOCATIF (ID_PROPRIETAIRE, ID_BATIMENT, NUMERO_APPARTEMENT, RIB, TARIF_HAUTE, TARIF_MOYEN, TARIF_BASSE, ANNEE_DE_LOCATION, ETAT)
    VALUES
    (3, 'B001', 101, 'FR7630004000031234567890143', 1200.00, 1000.00, 800.00, 2023, 'Actif'),
    (3, 'B002', 102, 'FR7630004000031234567890144', 1500.00, 1300.00, 1100.00, 2023, 'Actif'),
    (3, 'B003', 103, 'FR7630004000031234567890145', 1800.00, 1600.00, 1400.00, 2023, 'Actif'),
    (3, 'B001', 104, 'FR7630004000031234567890146', 900.00, 750.00, 600.00, 2023, 'Actif'),
    (3, 'B002', 105, 'FR7630004000031234567890147', 2200.00, 2000.00, 1800.00, 2023, 'Actif'),
    (3, 'B003', 106, 'FR7630004000031234567890148', 1100.00, 950.00, 800.00, 2023, 'Actif'),
    (3, 'B001', 107, 'FR7630004000031234567890149', 1400.00, 1200.00, 1000.00, 2023, 'Actif'),
    (3, 'B002', 108, 'FR7630004000031234567890150', 1700.00, 1500.00, 1300.00, 2023, 'Actif'),
    (3, 'B003', 109, 'FR7630004000031234567890151', 950.00, 800.00, 650.00, 2023, 'Actif'),
    (3, 'B001', 110, 'FR7630004000031234567890152', 2400.00, 2200.00, 2000.00, 2023, 'Actif'),
    (3, 'B002', 111, 'FR7630004000031234567890153', 1050.00, 900.00, 750.00, 2023, 'Actif'),
    (3, 'B003', 112, 'FR7630004000031234567890154', 1250.00, 1100.00, 950.00, 2023, 'Actif');
        
        -- Insertion dans la table EXCLUSIONS
        INSERT INTO EXCLUSIONS (ID_CONTRAT, DATE_DEBUT, DATE_FIN) VALUES
        (1, '2023-01-01', '2023-01-15'),
        (2, '2023-02-01', '2023-02-15'),
        (3, '2023-03-01', '2023-03-15');

        -- Insertion dans la table EQUIPEMENTS
        INSERT INTO EQUIPEMENTS (NUMERO_SERIE, ID_BATIMENT, NUMERO_APPARTEMENT, TYPE_EQUIPEMENT, DATE_ACHAT, CARACTERISTIQUES) VALUES
        ('EQ001', 'B001', 101, 'TV', '2022-01-01', 'Smart TV 42 pouces'),
        ('EQ002', 'B002', 102, 'Frigo', '2022-02-01', 'Frigo 300L'),
        ('EQ003', 'B003', 103, 'Lave-linge', '2022-03-01', 'Lave-linge 8kg');


        -- Insertion dans la table CONTRAT_DE_RESERVATION
        INSERT INTO CONTRAT_DE_RESERVATION (ID_LOCATAIRE, ID_BATIMENT, NUMERO_APPARTEMENT, DATE_RESERVATION, ARRHES, SOLDE, ETAT) VALUES
        (2, 'B001', 101, '2023-01-01', 'Payé', 'Non_Payé', 'Confirmé'),
        (2, 'B002', 102, '2023-02-01', 'Non_Payé', 'Non_Payé', 'Confirmé'),
        (2, 'B003', 103, '2023-03-01', 'Payé', 'Payé', 'Confirmé');

        -- Insertion dans la table SEMAINES_LOUEES
        INSERT INTO SEMAINES_LOUEES (NUMERO_CONTRAT, DATE_DEBUT, DATE_FIN) VALUES
        (1, '2023-01-01', '2023-01-07'),
        (2, '2023-02-01', '2023-02-07'),
        (3, '2023-03-01', '2023-03-07');

        







