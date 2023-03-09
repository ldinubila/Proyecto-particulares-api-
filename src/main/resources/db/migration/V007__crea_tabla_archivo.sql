CREATE TABLE Archivo(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    datos bytea NULL,
    extension varchar(255) NULL,
    tamanio float NOT NULL,
    id_modelo serial NOT NULL
);

ALTER TABLE Archivo ADD CONSTRAINT FK_Archivo_Modelo FOREIGN KEY(id_modelo)
    REFERENCES Modelo (id);