package com.prueba.usuarios.service.impl;

import com.prueba.usuarios.controller.UsuarioController;
import com.prueba.usuarios.model.dao.ITelefonoDao;
import com.prueba.usuarios.model.dao.IUsuarioDao;
import com.prueba.usuarios.model.entity.Usuario;
import com.prueba.usuarios.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private ITelefonoDao telefonoDao;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        setUsuario(usuario);
        usuario.getPhones().stream().collect(Collectors.toList()).forEach(telefonoDao::save);
        return usuarioDao.save(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioDao.findByemail(email);
    }

    private void setUsuario(Usuario usuario) {
        usuario.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        usuario.setFecha_creacion(new Date());
        usuario.setFecha_ult_actualizacion(new Date());
        usuario.setLast_login(new Date());
        usuario.setIsActive(true);
        usuario.setToken(UUID.randomUUID().toString());
    }
}
