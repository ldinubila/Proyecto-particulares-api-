CREATE TABLE Profesor(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    experiencia varchar(255) NULL,
    localidad varchar(255) NULL,
    video bytea NULL,
    Usuario_id serial NOT NULL
);

ALTER TABLE Profesor ADD CONSTRAINT FK_Profesor_Usuario FOREIGN KEY(Usuario_id)
    REFERENCES Usuario (id);