CREATE TABLE IF NOT EXISTS codes
(
    id  uuid ,
    code  VARCHAR(20) NOT NULL ,
    typeOfVocabulary  VARCHAR(10) NOT NULL ,
    coderOrganization  VARCHAR(3) NOT NULL ,
    version  VARCHAR(35) NOT NULL
);
