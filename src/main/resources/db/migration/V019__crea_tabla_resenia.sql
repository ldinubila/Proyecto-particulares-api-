CREATE TABLE Resenia(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    puntaje bigint NULL,
    comentario varchar(20) NULL,
    fecha TIMESTAMP(7) NOT NULL,
    id_usuario bigint,
    id_producto bigint
);

ALTER TABLE Resenia ADD CONSTRAINT FK_Resenia_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);

ALTER TABLE Resenia ADD CONSTRAINT FK_Resenia_Producto FOREIGN KEY(id_producto)
    REFERENCES Producto (id);