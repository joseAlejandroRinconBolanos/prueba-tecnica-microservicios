package com.microservicios.servicio_inventario.Client;

import com.microservicios.servicio_inventario.model.InventarioProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "producto-service", url = "${app.producto-service.url}")
public interface ProductoClient {
    @GetMapping("/api/productos/{id}")
    ResponseEntity<Map<String, Object>> obtenerProducto(@PathVariable Long id);
}
