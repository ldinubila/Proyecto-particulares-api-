CREATE TABLE DetalleClase(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    cupoRestante int NOT NULL,
    estado varchar(255) NULL,
    fecha TIMESTAMP(7) NULL,
    url varchar(255) NULL,
    id_clase bigint NULL
);

ALTER TABLE DetalleClase ADD CONSTRAINT FK_DetalleClase_Clase FOREIGN KEY(id_clase)
    REFERENCES Clase (id);