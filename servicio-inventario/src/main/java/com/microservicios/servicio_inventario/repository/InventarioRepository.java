package com.microservicios.servicio_inventario.repository;

import com.microservicios.servicio_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
