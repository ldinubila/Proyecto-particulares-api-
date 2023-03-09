CREATE TABLE Archivo_Dato_academico(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    datos bytea NULL,
    extension varchar(255) NULL,
    tamanio float NOT NULL,
    id_dato_academico bigint NULL
);

ALTER TABLE Archivo_Dato_academico ADD CONSTRAINT FK_Archivo_dato_academico FOREIGN KEY(id_dato_academico)
    REFERENCES Datos_Academicos (id);
