CREATE TABLE Mensaje(
    id serial NOT NULL PRIMARY KEY UNIQUE,
    asunto varchar(255) NULL,
    contenido varchar(255) NULL,
    eliminadoEmisor boolean NULL,
    eliminadoReceptor boolean NULL,
    fecha TIMESTAMP(7) NULL,
    leidoEmisor boolean NULL,
    leidoReceptor boolean NULL,
    id_emisor bigint NULL,
    id_receptor bigint NULL,
    id_respuesta bigint NULL
);

ALTER TABLE Mensaje ADD CONSTRAINT FK_Mensaje_UsuarioReceptor FOREIGN KEY(id_receptor)
    REFERENCES Usuario (id);

ALTER TABLE Mensaje ADD CONSTRAINT FK_Mensaje_MensjaeRespuesta FOREIGN KEY(id_respuesta)
    REFERENCES Mensaje (id);

ALTER TABLE Mensaje ADD CONSTRAINT FK_Mensaje_UsuarioEmisor FOREIGN KEY(id_emisor)
    REFERENCES Usuario (id);