package com.tienda_mascotas.demo.servicios;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda_mascotas.demo.entidades.Usuario;
import com.tienda_mascotas.demo.repositorios.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;




    // Registrar usuario
    public Usuario registrarCliente(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    // Login
    public Usuario login(String nombre, String contraseña) throws Exception {

        // Cambia la búsqueda para utilizar el nombre en lugar del id
        Usuario usuario = usuarioRepositorio.findByNombre(nombre)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verifica la contraseña
        if (!usuario.getContraseña().equals(contraseña)) {
            throw new Exception("Contraseña incorrecta");
        }

        return usuario; // Retorna el usuario encontrado
    }
    // Obtener todos los usuarios
    public List<Usuario> consultarClientes() {
        return usuarioRepositorio.findAll();
    }
    public Usuario findById(String idUsuario) {
        return usuarioRepositorio.findById(Integer.valueOf(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }
}

