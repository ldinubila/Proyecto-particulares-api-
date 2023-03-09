CREATE TABLE PagoParticular(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    fecha TIMESTAMP(7) NULL,
    monto numeric(19, 2) NULL,
    id_producto bigint NULL,
    id_usuario bigint NULL
);

ALTER TABLE PagoParticular ADD CONSTRAINT FK_PagoParticular_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE PagoParticular ADD CONSTRAINT FK_PagoParticular_Producto FOREIGN KEY(id_producto)
    REFERENCES Producto (id);

