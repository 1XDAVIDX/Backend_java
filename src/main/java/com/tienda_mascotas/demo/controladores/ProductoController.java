package com.tienda_mascotas.demo.controladores;

import com.tienda_mascotas.demo.entidades.Producto;
import com.tienda_mascotas.demo.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController


@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoServicio productoServicio;

    @PostMapping("/registrar")
    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoServicio.registrarProducto(producto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> consultarProductos() {
        return ResponseEntity.ok(productoServicio.consultarProductos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable String id) {
        try {
            productoServicio.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Producto> modificarProducto(@PathVariable String id, @RequestBody Producto producto) {
        try {
            Producto productoModificado = productoServicio.modificarProducto(id, producto);
            return ResponseEntity.ok(productoModificado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
