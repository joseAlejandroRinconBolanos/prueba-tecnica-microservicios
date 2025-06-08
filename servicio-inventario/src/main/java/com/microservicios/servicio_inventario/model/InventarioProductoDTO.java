package com.microservicios.servicio_inventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class InventarioProductoDTO {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;

    public InventarioProductoDTO() {
        
    }
}
