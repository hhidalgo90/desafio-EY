package com.prueba.usuarios.model.dao;

import com.prueba.usuarios.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;


public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    Usuario findByemail(String email);
}
