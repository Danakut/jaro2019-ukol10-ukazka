CREATE DATABASE dollbase
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_czech_ci;

USE dollbase;

CREATE TABLE dolls
(
    ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    jmeno VARCHAR(30),
    vrsek VARCHAR(20) NOT NULL,
    spodek VARCHAR(20) NOT NULL,
    datumVzniku TINYINT DEFAULT 0,
    verze INT DEFAULT 0
);

CREATE USER  'dolluser'   IDENTIFIED BY 'dolluspass';
GRANT ALL PRIVILEGES ON dollbase.* TO   'dolluser'@'%';

