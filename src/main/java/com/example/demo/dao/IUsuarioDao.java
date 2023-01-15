package com.example.demo.dao;

import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {


    Usuario findByUsuario(String usuario);
}
