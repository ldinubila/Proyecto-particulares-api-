CREATE TABLE Perfil(
    Id serial NOT NULL PRIMARY KEY UNIQUE,
    Apellido varchar(255) NULL,
    Documento bigint NULL,
    Email varchar(255) NULL UNIQUE,
    Nombre varchar(255) NULL,
    Telefono bigint NULL,
    Usuario_id bigint NULL
);

ALTER TABLE Perfil ADD CONSTRAINT FK_Perfil_Usuario FOREIGN KEY(Usuario_id)
    REFERENCES Usuario (id);