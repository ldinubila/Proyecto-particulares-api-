CREATE TABLE ArchivoDeResolucion(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    datos bytea NULL,
    extension varchar(255) NULL,
    tamanio float NOT NULL,
    id_resolucion serial NOT NULL
);

ALTER TABLE ArchivoDeResolucion ADD CONSTRAINT FK_ArchivoDeResolucion_Resolucion FOREIGN KEY(id_resolucion)
    REFERENCES Resolucion (id);


