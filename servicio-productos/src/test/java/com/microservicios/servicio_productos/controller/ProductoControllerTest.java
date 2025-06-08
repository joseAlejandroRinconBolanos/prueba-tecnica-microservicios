package com.microservicios.servicio_productos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.servicio_productos.model.Producto;
import com.microservicios.servicio_productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
//@Import(ProductoControllerTest.Config.class)

public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    private ObjectMapper objectMapper;
    private Producto producto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setPrecio(BigDecimal.valueOf(1500.0));
    }

    @Test
    void testGetAllProductos() throws Exception {
        Page<Producto> page = new PageImpl<>(List.of(producto), PageRequest.of(0, 10), 1);

        Mockito.when(productoRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].nombre").value("Laptop"));
    }

    @Test
    void testGetById_ProductoExistente() throws Exception {
        Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.nombre").value("Laptop"));
    }

    @Test
    void testGetById_ProductoNoExistente() throws Exception {
        Mockito.when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearProducto_Exitoso() throws Exception {
        Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.nombre").value("Laptop"))
                .andExpect(jsonPath("$.meta.message").value("Producto creado exitosamente"));
    }

    @Test
    void testCrearProducto_NombreInvalido() throws Exception {
        Producto productoSinNombre = new Producto();
        productoSinNombre.setPrecio(BigDecimal.valueOf(500.0));

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoSinNombre)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.error").value("El nombre del producto es obligatorio"));
    }

    @Test
    void testEliminarProductoExistente() throws Exception {
        Mockito.when(productoRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.success").value(true));
    }

    @Test
    void testEliminarProductoNoExistente() throws Exception {
        Mockito.when(productoRepository.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/productos/999"))
                .andExpect(status().isNotFound());
    }
}
