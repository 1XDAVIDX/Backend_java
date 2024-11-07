package com.tienda_mascotas.demo.controladores;

import com.tienda_mascotas.demo.entidades.Compra;
import com.tienda_mascotas.demo.servicios.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraServicio compraServicio;

    // Crear compra
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarCompra(@RequestBody Compra compra) {
        try {
            compraServicio.realizarCompra(compra);  // Delegamos la lógica al servicio
            return ResponseEntity.ok("Compra realizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en la compra: " + e.getMessage());
        }
    }

    // Obtener lista de compras
    @GetMapping("/listar")
    public ResponseEntity<List<Compra>> obtenerCompras() {
        return ResponseEntity.ok(compraServicio.obtenerCompras());
    }

    // Completar compra
    @DeleteMapping("/compra/completada/{idCompra}/{idUsuario}")
    public ResponseEntity<Map<String, Object>> completarCompra(@PathVariable int idCompra, @PathVariable String idUsuario) {
        try {
            // Lógica para completar la compra (actualizar estado o eliminar)
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Compra completada");
            response.put("idCompra", idCompra);
            response.put("idUsuario", idUsuario);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al completar la compra");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
