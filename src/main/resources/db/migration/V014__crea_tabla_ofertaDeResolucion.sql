CREATE TABLE OfertaDeResolucion(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    costo numeric(19, 2) NULL,
    estado varchar(255) NULL,
    fechaDeCreacion TIMESTAMP(7) NULL,
    id_tipoDeDemora serial NOT NULL,
    id_modelo serial NOT NULL,
    id_tipoDeResolucion serial NOT NULL,
    id_usuario serial NOT NULL
);

ALTER TABLE OfertaDeResolucion ADD CONSTRAINT FK_OfertaDeResolucion_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE OfertaDeResolucion ADD CONSTRAINT FK_OfertaDeResolucion_Modelo FOREIGN KEY(id_modelo)
    REFERENCES Modelo (id);

ALTER TABLE OfertaDeResolucion  ADD CONSTRAINT FK_OfertaDeResolucion_TipoDeDemora FOREIGN KEY(id_tipoDeDemora)
    REFERENCES TipoDeDemora (id);

ALTER TABLE OfertaDeResolucion ADD CONSTRAINT FK_OfertaDeResolucion_TipoDeResolucion FOREIGN KEY(id_tipoDeResolucion)
    REFERENCES TipoDeResolucion (id);


