package com.tienda_mascotas.demo.controladores;

import com.tienda_mascotas.demo.entidades.Compra;
import com.tienda_mascotas.demo.entidades.Producto;
import com.tienda_mascotas.demo.entidades.Usuario;
import com.tienda_mascotas.demo.servicios.CompraServicio;
import com.tienda_mascotas.demo.servicios.ProductoServicio;
import com.tienda_mascotas.demo.servicios.UsuarioServicio;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProductoServicio productoServicio;

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
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar la compra
            Compra compra = compraServicio.findById(idCompra);
            if (compra == null) {
                throw new EntityNotFoundException("Compra no encontrada");
            }

            // Buscar el usuario
            Usuario usuario = compra.getUsuario();

            // Buscar el producto relacionado con la compra
            Producto producto = compra.getProducto();

            // Validar stock suficiente
            if (producto.getStock() < compra.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para completar la compra");
            }

            // Marcar compra como completada y actualizar stock
            producto.setStock(producto.getStock() - compra.getCantidad());
            productoServicio.save(producto);

            // Construir la respuesta
            response.put("message", "Compra completada");
            response.put("idProducto", producto.getIdProducto());
            response.put("cantidad", compra.getCantidad());  // Aquí ya se refleja el stock actualizado
            response.put("total", compra.getTotal());
            response.put("idCompra", idCompra);
            response.put("idUsuario", idUsuario);
            response.put("nombre", usuario.getNombre());
            response.put("correo", usuario.getEmail());
            compraServicio.eliminarCompra(idCompra,idUsuario);

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            response.put("message", "Error interno al completar la compra");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




}
