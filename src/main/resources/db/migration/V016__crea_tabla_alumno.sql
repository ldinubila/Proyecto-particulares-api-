CREATE TABLE Alumno(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    materiasInteres varchar(255) NULL,
    id_nivel bigint,
    Usuario_id serial NOT NULL
);

ALTER TABLE Alumno ADD CONSTRAINT FK_Alumno_Nivel FOREIGN KEY(id_nivel)
    REFERENCES Nivel (id);

ALTER TABLE Alumno ADD CONSTRAINT FK_Alumno_Usuario FOREIGN KEY(Usuario_id)
    REFERENCES Usuario (id);


