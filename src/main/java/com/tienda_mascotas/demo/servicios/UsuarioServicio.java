package com.tienda_mascotas.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda_mascotas.demo.entidades.Usuario;
import com.tienda_mascotas.demo.repositorios.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    // Mostrar ID de usuarios
    public List<String> mostrarId() {
        return usuarioRepositorio.findAll().stream()
                .map(Usuario::getIdUsuario)
                .toList();
    }

    // Registrar usuario sin encriptación
    public Usuario registrarCliente(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    // Login
    public Usuario login(String idUsuario, String contraseña) throws Exception {
        // Lógica para autenticar al usuario
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!usuario.getContraseña().equals(contraseña)) {
            throw new Exception("Contraseña incorrecta");
        }

        return usuario; // Retorna el usuario encontrado
    }
    // Obtener todos los usuarios
    public List<Usuario> consultarClientes() {
        return usuarioRepositorio.findAll();
    }
}

