/*================================================*/
/* Table: family */
/*================================================*/

DROP TABLE IF EXISTS family;
CREATE TABLE family
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    familyName   VARCHAR(32) NOT NULL,
    nrOfAdults   INT         NOT NULL,
    nrOfChildren INT         NOT NULL,
    nrOfInfants  INT         NOT NULL
)