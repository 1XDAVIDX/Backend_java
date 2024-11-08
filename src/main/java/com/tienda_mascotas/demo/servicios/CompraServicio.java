package com.tienda_mascotas.demo.servicios;

import com.tienda_mascotas.demo.entidades.Compra;
import com.tienda_mascotas.demo.entidades.Producto;
import com.tienda_mascotas.demo.entidades.Usuario;
import com.tienda_mascotas.demo.repositorios.CompraRepositorio;
import com.tienda_mascotas.demo.repositorios.ProductoRepositorio;
import com.tienda_mascotas.demo.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CompraServicio {
    @Autowired
    private CompraRepositorio compraRepositorio;
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void realizarCompra(Compra compra) throws Exception {

        Usuario usuario = usuarioRepositorio.findById(compra.getUsuario().getIdUsuario())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));


        Producto producto = productoRepositorio.findById(compra.getProducto().getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));


        if (producto.getStock() < compra.getCantidad()) {
            throw new Exception("Producto agotado");
        }


        BigDecimal total = producto.getPrecio().multiply(new BigDecimal(compra.getCantidad()));
        compra.setTotal(total);


        producto.setStock(producto.getStock() - compra.getCantidad());
        productoRepositorio.save(producto);


        compraRepositorio.save(compra);
    }

    public List<Compra> obtenerCompras() {
        return compraRepositorio.findAll();
    }
    public Compra eliminarCompra(int idCompra, String idUsuario) throws Exception {

        Compra compra = compraRepositorio.findById(idCompra)
                .orElseThrow(() -> new Exception("Compra no encontrada"));


        Usuario usuario = usuarioRepositorio.findById(Integer.valueOf(idUsuario))
                .orElseThrow(() -> new Exception("Usuario no encontrado"));


        if (!compra.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new Exception("La compra no pertenece al usuario proporcionado");
        }


        compraRepositorio.delete(compra);


        return compra;
    }

}
