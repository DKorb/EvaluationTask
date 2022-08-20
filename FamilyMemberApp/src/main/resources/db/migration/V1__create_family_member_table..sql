/*================================================*/
/* Table: familymember */
/*================================================*/

DROP TABLE IF EXISTS familymember;
CREATE TABLE familymember
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    givenName  VARCHAR(32) NOT NULL,
    familyName VARCHAR(32) NOT NULL,
    age        INT         NOT NULL,
    familyId   INT         NOT NULL
)