package com.tienda_mascotas.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tienda_mascotas.demo.entidades.Usuario;
import com.tienda_mascotas.demo.servicios.UsuarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Mostrar ID de usuarios
    @GetMapping("/ID")
    public List<String> mostrarId() {
        return usuarioServicio.mostrarId();
    }

    // Registrar cliente (sin encriptación de contraseña)
    @PostMapping("/insertar/usuario")
    public Usuario registrarCliente(@RequestBody Usuario usuario) {
        return usuarioServicio.registrarCliente(usuario);
    }

    // Login sin encriptación
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String id_usuario, @RequestParam String contraseña) {
        try {
            // Verifica las credenciales del usuario a través del servicio
            Usuario usuario = usuarioServicio.login(id_usuario, contraseña);

            // Crear una respuesta con el mensaje y el rol
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso");
            response.put("rol", usuario.getRol()); // Devuelve el rol del usuario (por ejemplo, "admin" o "cliente")

            // Retorna el mapa con la respuesta
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En caso de error, retornar un error 401 (Unauthorized)
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    // Consultar todos los usuarios
    @GetMapping("/consultarClientes")
    public List<Usuario> consultarClientes() {
        return usuarioServicio.consultarClientes();
    }
}
