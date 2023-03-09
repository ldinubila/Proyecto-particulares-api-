ALTER TABLE OfertaDeResolucion ADD CONSTRAINT FK_ofertaResolucion_producto FOREIGN KEY(id)
    REFERENCES Producto (id);