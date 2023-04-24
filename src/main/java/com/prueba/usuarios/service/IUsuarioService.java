package com.prueba.usuarios.service;


import com.prueba.usuarios.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> findAll();

    Usuario save(Usuario usuario);

    Usuario findById(Long id);

    Usuario findByEmail(String email);

    void delete(Long id);
}