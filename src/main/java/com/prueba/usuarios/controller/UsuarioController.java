package com.prueba.usuarios.controller;

import com.prueba.usuarios.model.entity.Usuario;
import com.prueba.usuarios.service.IUsuarioService;
import com.prueba.usuarios.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping()
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @PostMapping()
    public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result){
        Map<String, Object> respuesta = new HashMap<>();
        Usuario nuevoUsuario = null;
        if(result.hasErrors()){
            List errores = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' ," + err.getDefaultMessage())
                    .collect(Collectors.toList());

            respuesta.put("mensaje" , errores);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try {
            if(usuarioService.findByEmail(usuario.getEmail()) != null) {
                respuesta.put("mensaje" , "El correo ya esta registrado");
                return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
            }
            if (!PasswordUtil.validarPassword(usuario.getPassword())) {
                respuesta.put("mensaje" , "Password debe tener al menos una mayuscula, letras minusculas y dos numeros");
                return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
            }
            log.info("[guardar] Usuario: " + usuario.toString());
            nuevoUsuario = usuarioService.save(usuario);
        }catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al registrar usuario: ".concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje" , "usuario creado con exito");
        respuesta.put("usuario" , nuevoUsuario);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //Responde 204, sin contenido
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            usuarioService.delete(id);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al eliminar Usuario: ".concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "Usuario eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Map<String, Object> respuesta = new HashMap<>();
        Usuario retorno = null;
        try {
            retorno = usuarioService.findById(id);
        } catch (DataAccessException e){
            respuesta.put("mensaje" , "Error al consultar usuario: ".concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(retorno == null){

            respuesta.put("mensaje" , "El usuario ID: ".concat(id.toString()).concat(" no existe!!"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Usuario>(retorno, HttpStatus.OK);
        }
    }
}
