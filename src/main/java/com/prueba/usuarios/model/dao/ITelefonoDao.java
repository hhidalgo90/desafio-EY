package com.prueba.usuarios.model.dao;

import com.prueba.usuarios.model.entity.Telefono;
import org.springframework.data.repository.CrudRepository;

public interface ITelefonoDao extends CrudRepository<Telefono, Long> {
}
