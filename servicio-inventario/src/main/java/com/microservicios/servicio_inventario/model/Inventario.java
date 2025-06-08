package com.microservicios.servicio_inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Inventario {
    @Id
    private Long productoId;

    @Column(nullable = false)
    private Integer cantidad;

    public Inventario(Long productoId, int i) {
    }
}
