CREATE TABLE Usuario(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    Email varchar(255) NULL UNIQUE,
    apellido varchar(255) NULL,
    bloqueado boolean NOT NULL,
    contrasenia varchar(255) NULL,
    documento bigint NULL,
    fechaNacimiento date NULL,
    fotoPerfil bytea NULL,
    nombre varchar(255) NULL,
    rol varchar(255) NULL,
    telefono bigint NULL
)
