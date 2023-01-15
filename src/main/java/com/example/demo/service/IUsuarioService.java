package com.example.demo.service;

import com.example.demo.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> findAll();

    Usuario findByUsuario(String usuario);

    Usuario save(Usuario usuario);

    Usuario findById(Long id);

    void delete(Long id);
}
