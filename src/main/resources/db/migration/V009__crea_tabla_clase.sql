CREATE TABLE Clase(
    id serial NOT NULL PRIMARY KEY,
    cupo int NOT NULL,
    nombre varchar(50) NULL,
    precio numeric(19, 2) NULL,
    descripcion varchar(255) NULL,
    fecha TIMESTAMP(7) NULL,
    metodo varchar(255) NULL,
    modo varchar(255) NULL,
    puntuacion int NOT NULL,
    id_materia serial NOT NULL,
    id_modelo bigint NULL,
    id_nivel serial NOT NULL,
    id_profesor serial NOT NULL
);

ALTER TABLE Clase ADD CONSTRAINT FK_clase_modelo FOREIGN KEY(id_modelo)
    REFERENCES Modelo (id);

ALTER TABLE Clase ADD CONSTRAINT FK_clase_profesor FOREIGN KEY(id_profesor)
    REFERENCES Profesor (id);

ALTER TABLE Clase ADD CONSTRAINT FK_clase_nivel FOREIGN KEY(id_nivel)
    REFERENCES Nivel (id);

ALTER TABLE Clase ADD CONSTRAINT FK_clase_materia FOREIGN KEY(id_materia)
    REFERENCES Materia (id);