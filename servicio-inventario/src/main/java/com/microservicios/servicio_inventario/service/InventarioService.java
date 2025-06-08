package com.microservicios.servicio_inventario.service;

import com.microservicios.servicio_inventario.Client.ProductoClient;
import com.microservicios.servicio_inventario.model.Inventario;
import com.microservicios.servicio_inventario.model.InventarioProductoDTO;
import com.microservicios.servicio_inventario.repository.InventarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository repository;
    private final ProductoClient productoClient;

    public InventarioProductoDTO consultarDisponibilidad(Long productoId) {
        ResponseEntity<Map<String, Object>> response = productoClient.obtenerProducto(productoId);
        Map<String, Object> body = response.getBody();
        System.out.println("response: "+ response);
        System.out.println("Body: "+ body);

        if (body == null || !body.containsKey("nombre")) {
            throw new RuntimeException("Producto no encontrado o respuesta inválida");
        }

        String nombreProducto = (String) body.get("nombre");

        Inventario inventario = repository.findById(productoId)
                .orElse(new Inventario(productoId, 0));

        System.out.printf("[EVENTO] Consulta de inventario - Producto ID: %d%n", productoId);

        InventarioProductoDTO dto = new InventarioProductoDTO();
        dto.setProductoId(productoId);
        dto.setCantidad(inventario.getCantidad());
        dto.setNombreProducto(nombreProducto);

        return dto;
    }

    public void actualizarInventario(Long productoId, int cantidadVendida) {
        Inventario inventario = repository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en inventario"));

        inventario.setCantidad(inventario.getCantidad() - cantidadVendida);
        repository.save(inventario);

        System.out.println("Inventario actualizado para producto ID " + productoId + ". Nueva cantidad: " + inventario.getCantidad());
    }

}
