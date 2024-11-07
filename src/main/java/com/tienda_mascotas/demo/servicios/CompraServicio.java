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
        // Obtener el usuario desde la base de datos
        Usuario usuario = usuarioRepositorio.findById(compra.getUsuario().getIdUsuario())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Obtener el producto desde la base de datos
        Producto producto = productoRepositorio.findById(compra.getProducto().getIdProducto())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        // Validar stock
        if (producto.getStock() < compra.getCantidad()) {
            throw new Exception("Producto agotado");
        }

        // Calcular el total de la compra
        BigDecimal total = producto.getPrecio().multiply(new BigDecimal(compra.getCantidad()));
        compra.setTotal(total);

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - compra.getCantidad());
        productoRepositorio.save(producto);

        // Guardar la compra
        compraRepositorio.save(compra);
    }

    public List<Compra> obtenerCompras() {
        return compraRepositorio.findAll();
    }
    public Compra eliminarCompra(int idCompra, String idUsuario) throws Exception {
        // Buscar la compra en la base de datos
        Compra compra = compraRepositorio.findById(idCompra)
                .orElseThrow(() -> new Exception("Compra no encontrada"));

        // Buscar al usuario en la base de datos
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verificar si la compra corresponde al usuario
        if (!compra.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new Exception("La compra no pertenece al usuario proporcionado");
        }

        // Eliminar la compra de la base de datos
        compraRepositorio.delete(compra);

        // Retornar los detalles de la compra y del usuario
        return compra;  // Aquí puedes devolver los detalles de la compra eliminada
    }

}
