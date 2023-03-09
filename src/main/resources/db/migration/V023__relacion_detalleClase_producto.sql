ALTER TABLE DetalleClase ADD CONSTRAINT FK_detalleClase_producto
    FOREIGN KEY(id)
    REFERENCES Producto (id);