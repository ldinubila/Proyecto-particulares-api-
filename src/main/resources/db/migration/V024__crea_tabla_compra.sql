CREATE TABLE Compra(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    estado varchar(255) NULL,
    fecha TIMESTAMP(7) NULL,
    identificadorExterno varchar(255) NULL,
    monto numeric(19, 2) NULL,
    nombre varchar(255) NULL,
    id_producto serial NOT NULL,
    id_usuario serial NOT NULL
);

ALTER TABLE Compra ADD CONSTRAINT FK_Compra_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE Compra ADD CONSTRAINT FK_Compra_Producto FOREIGN KEY(id_producto)
    REFERENCES Producto (id);