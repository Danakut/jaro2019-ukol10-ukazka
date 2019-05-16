CREATE DATABASE skladPanenek
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_czech_ci;

USE skladPanenek;

CREATE TABLE panenky
(
    ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    jmeno VARCHAR(40),
    vrsek VARCHAR(25),
    spodek VARCHAR(25),
    datumVzniku VARCHAR(15),
    verze INT DEFAULT 0
);

CREATE USER  'student'   IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON skladPanenek.* TO 'student'@'%';

INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES ("Amélie", "javagirl_top01.png", "javagirl_bottom01.png", "2019-05-06");
INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES ("Bronislava", "javagirl_top02.png", "javagirl_bottom02.png", "2019-05-07");
INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES ("Celestýna", "javagirl_top03.png", "javagirl_bottom03.png", "2019-05-08");
INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES ("Dora", "javagirl_top04.png", "javagirl_bottom04.png", "2019-05-09");
INSERT INTO panenky (jmeno, vrsek, spodek, datumVzniku) VALUES ("Evženie", "javagirl_top05.png", "javagirl_bottom05.png", "2019-05-10");

