CREATE TABLE Direccion(
    Id serial NOT NULL PRIMARY KEY UNIQUE,
    Calle varchar(255) NULL,
    Numero int NOT NULL,
    Localidad_Id bigint NULL
);

ALTER TABLE Direccion ADD CONSTRAINT FK_Direccion_Localidad FOREIGN KEY(Localidad_Id)
    REFERENCES Localidad (Id);