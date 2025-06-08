package com.microservicios.servicio_productos.controller;

import com.microservicios.servicio_productos.model.Producto;
import com.microservicios.servicio_productos.repository.ProductoRepository;
//import org.hibernate.query.Page;
//import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import org.springframework.hateoas.CollectionModel;
//import com.toedter.spring.hateoas.jsonapi.JsonApiModel;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository repository;

    public ProductoController(ProductoRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Producto> productos = repository.findAll(PageRequest.of(page, size));

        return ResponseEntity.ok(JsonApiModelBuilder.jsonApiModel()
                .model(CollectionModel.of(productos.getContent()))
                .meta("totalElements", productos.getTotalElements())
                .meta("totalPages", productos.getTotalPages())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return (ResponseEntity<?>) repository.findById(id)
                .map(producto -> ResponseEntity.ok(
                        JsonApiModelBuilder.jsonApiModel()
                                .model(producto)  // Modelo individual
                                .build()
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        try {
            // Validación adicional si es necesaria
            if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        JsonApiModelBuilder.jsonApiModel()
                                .meta("error", "El nombre del producto es obligatorio")
                                .build());
            }

            Producto savedProduct = repository.save(producto);

            return ResponseEntity.ok(
                    JsonApiModelBuilder.jsonApiModel()
                            .model(savedProduct)
                            .meta("message", "Producto creado exitosamente")
                            .build());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    JsonApiModelBuilder.jsonApiModel()
                            .meta("error", "Error al crear producto: " + e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok(
                    JsonApiModelBuilder.jsonApiModel()
                            .meta("success", true)
                            .build()
            );
        }
        return ResponseEntity.notFound().build();
    }
}
