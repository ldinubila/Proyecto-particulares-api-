CREATE TABLE Datos_Academicos(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    documento bytea NULL,
    fechaFin date NULL,
    fechaInicio date NULL,
    titulo varchar(255) NULL,
    profesor_id bigint NULL
);

ALTER TABLE Datos_Academicos ADD CONSTRAINT FK_DatosAcademicos_Profesor FOREIGN KEY(profesor_id)
    REFERENCES Profesor (id);