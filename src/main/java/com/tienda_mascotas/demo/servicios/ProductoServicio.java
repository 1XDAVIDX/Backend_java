package com.tienda_mascotas.demo.servicios;

import com.tienda_mascotas.demo.entidades.Producto;
import com.tienda_mascotas.demo.repositorios.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    public Producto registrarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public List<Producto> consultarProductos() {
        return productoRepositorio.findAll();
    }

    public void eliminarProducto(String idProducto) throws Exception {
        Producto producto = productoRepositorio.findById(idProducto)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        productoRepositorio.delete(producto);
    }

    public Producto modificarProducto(String idProducto, Producto productoDetalles) throws Exception {
        Producto producto = productoRepositorio.findById(idProducto)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        producto.setNombre(productoDetalles.getNombre());
        producto.setDescripcion(productoDetalles.getDescripcion());
        producto.setPrecio(productoDetalles.getPrecio());
        producto.setStock(productoDetalles.getStock());
        return productoRepositorio.save(producto);
    }
}
