package com.prueba.usuarios.controller;

import com.prueba.usuarios.model.entity.Usuario;
import com.prueba.usuarios.service.IUsuarioService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private static final String REGEX_PASSWORD = "^(?=.*[0-9]{2})(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

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
            if (!validarPassword(usuario.getPassword())) {
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

    private boolean validarPassword(String password) {
        Pattern regex = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = regex.matcher(password);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
}
