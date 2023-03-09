CREATE TABLE Modelo(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    estado varchar(255) NULL,
    fecha TIMESTAMP(7) NULL,
    nombre varchar(255) NULL,
    publico boolean NOT NULL,
    id_institucion serial NOT NULL,
    id_carrera serial NOT NULL,
    id_materia serial NOT NULL,
    id_nivel serial NOT NULL,
    id_usuario serial NOT NULL
);

ALTER TABLE Modelo ADD CONSTRAINT FK_Modelo_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE Modelo ADD CONSTRAINT FK_Modelo_Materia FOREIGN KEY(id_materia)
    REFERENCES Materia (id);

ALTER TABLE Modelo ADD CONSTRAINT FK_Modelo_Nivel FOREIGN KEY(id_nivel)
    REFERENCES Nivel (id);



