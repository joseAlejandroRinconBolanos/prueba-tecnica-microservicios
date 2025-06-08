package com.microservicios.servicio_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.servicio_inventario.model.InventarioProductoDTO;
import com.microservicios.servicio_inventario.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService service;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testConsultarDisponibilidad() throws Exception {
        InventarioProductoDTO dto = new InventarioProductoDTO(1L, "Laptop HP", 10);
        when(service.consultarDisponibilidad(1L)).thenReturn(dto);

        mockMvc.perform(get("/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.nombreProducto").value("Laptop HP"))
                .andExpect(jsonPath("$.cantidad").value(10));
    }

    @Test
    void testActualizarInventario() throws Exception {
        mockMvc.perform(put("/inventarios/1/actualizar")
                        .param("cantidadVendida", "3"))
                .andExpect(status().isOk());

        verify(service).actualizarInventario(1L, 3);
    }
}
