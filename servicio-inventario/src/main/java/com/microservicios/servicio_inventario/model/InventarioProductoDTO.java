package com.microservicios.servicio_inventario.model;

import lombok.Data;

@Data
public class InventarioProductoDTO {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
}
