CREATE TABLE Resolucion(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    comentario varchar(255) NULL,
    fecha TIMESTAMP(7) NULL,
    id_modelo serial NOT NULL,
    id_usuario serial NOT NULL
);

ALTER TABLE Resolucion ADD CONSTRAINT FK_Resolucion_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE Resolucion ADD CONSTRAINT FK_Resolucion_Modelo FOREIGN KEY(id_modelo)
    REFERENCES Modelo (id);


