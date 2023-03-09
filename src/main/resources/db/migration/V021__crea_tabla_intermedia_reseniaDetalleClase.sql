CREATE TABLE ReseniaDetalleClase(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    id_resenia bigint NULL,
    id_detalleClase bigint NULL
);

ALTER TABLE ReseniaDetalleClase ADD CONSTRAINT FK_ReseniaDetalleClase_Resenia
    FOREIGN KEY(id_resenia)
    REFERENCES Resenia (id);

ALTER TABLE ReseniaDetalleClase ADD CONSTRAINT FK_ReseniaDetalleClase_DetalleClase
    FOREIGN KEY(id_detalleClase)
    REFERENCES Resenia (id);