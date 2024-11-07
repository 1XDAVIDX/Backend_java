package com.tienda_mascotas.demo.repositorios;

import com.tienda_mascotas.demo.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra, Integer> {
}
