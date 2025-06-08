package com.microservicios.servicio_inventario.service;

import com.microservicios.servicio_inventario.Client.ProductoClient;
import com.microservicios.servicio_inventario.model.Inventario;
import com.microservicios.servicio_inventario.model.InventarioProductoDTO;
import com.microservicios.servicio_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
public class InventarioServiceTest {
    private InventarioRepository repository;
    private ProductoClient productoClient;
    private InventarioService service;

    @BeforeEach
    void setUp() {
        repository = mock(InventarioRepository.class);
        productoClient = mock(ProductoClient.class);
        service = new InventarioService(repository, productoClient);
    }

    @Test
    void consultarDisponibilidad_retornaInventarioProductoDTO() {
        Long productoId = 1L;

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("nombre", "Producto de prueba");

        Map<String, Object> data = new HashMap<>();
        data.put("attributes", attributes);

        Map<String, Object> body = new HashMap<>();
        body.put("data", data);

        when(productoClient.obtenerProducto(productoId)).thenReturn(ResponseEntity.ok(body));
        when(repository.findById(productoId)).thenReturn(Optional.of(new Inventario(productoId, 10)));

        InventarioProductoDTO dto = service.consultarDisponibilidad(productoId);

        assertNotNull(dto);
        assertEquals(productoId, dto.getProductoId());
        assertEquals(10, dto.getCantidad());
        assertEquals("Producto de prueba", dto.getNombreProducto());
    }

    @Test
    void actualizarInventario_actualizaCantidadCorrectamente() {
        Long productoId = 2L;
        Inventario inventario = new Inventario(productoId, 20);

        when(repository.findById(productoId)).thenReturn(Optional.of(inventario));

        service.actualizarInventario(productoId, 5);

        assertEquals(15, inventario.getCantidad());
        verify(repository, times(1)).save(inventario);
    }
}
