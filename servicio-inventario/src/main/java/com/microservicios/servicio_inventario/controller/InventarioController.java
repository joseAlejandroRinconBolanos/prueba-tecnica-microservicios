package com.microservicios.servicio_inventario.controller;

import com.microservicios.servicio_inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<?> getDisponibilidad(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.consultarDisponibilidad(productoId));
    }

    @PatchMapping("/{productoId}")
    public ResponseEntity<?> actualizarInventario(
            @PathVariable Long productoId,
            @RequestParam int cantidadVendida) {
        service.actualizarInventario(productoId, cantidadVendida);
        return ResponseEntity.ok().build();
    }
}
