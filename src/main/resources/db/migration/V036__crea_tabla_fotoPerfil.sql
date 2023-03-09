CREATE TABLE FotoPerfil(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    datos bytea NULL,
    extension varchar(255) NULL,
    tamanio float NOT NULL,
    id_usuario bigint NULL
);

ALTER TABLE FotoPerfil ADD CONSTRAINT FK_FotoPerfil_Usuario FOREIGN KEY(id_usuario)
    REFERENCES Usuario (id);
