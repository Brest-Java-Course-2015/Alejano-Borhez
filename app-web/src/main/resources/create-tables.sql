SET DATABASE SQL SYNTAX ORA TRUE;
DROP TABLE USER IF EXISTS;
CREATE TABLE USER (
userid INTEGER IDENTITY,
login VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
updatedDate TIMESTAMP NOT NULL
);