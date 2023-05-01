package com.prueba.usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.usuarios.controller.UsuarioController;
import com.prueba.usuarios.model.entity.Telefono;
import com.prueba.usuarios.model.entity.Usuario;
import com.prueba.usuarios.service.IUsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    IUsuarioService usuarioService;

    List<Telefono> telefonos = new ArrayList<Telefono>();
    Usuario usuario1 = new Usuario(1L, "Hector Hidalgo", "Hector123", "h.hidalgo1990@gmail.com", telefonos.stream().collect(Collectors.toSet()), true, new Date(), new Date(), new Date(), "43565423423423");
    Usuario usuario2 = new Usuario(1L, "Kathy Hidalgo", "Hector123", "h.hidalgo1990@gmail.com", telefonos.stream().collect(Collectors.toSet()), true, new Date(), new Date(), new Date(), "43565423423423");
    Usuario usuario3 = new Usuario(1L, "Erika Espinoza", "Hector123", "h.hidalgo1990@gmail.com", telefonos.stream().collect(Collectors.toSet()), true, new Date(), new Date(), new Date(), "43565423423423");

    @Test
    public void getAllRecords_success() throws Exception {
        List<Usuario> records = new ArrayList<>(Arrays.asList(usuario1, usuario2, usuario3));

        Mockito.when(usuarioService.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$[2].name", is("Erika Espinoza")));
    }

    @Test
    public void getUsuarioById_success() throws Exception {
        Mockito.when(usuarioService.findById(usuario1.getId())).thenReturn(usuario1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", notNullValue()))
                        .andExpect(jsonPath("$.name", is("Hector Hidalgo")));
    }

    @Test
    public void createUsuarioSuccess() throws Exception {
        Usuario record = usuario2;

        Mockito.when(usuarioService.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void deletePatientById_success() throws Exception {
        Mockito.when(usuarioService.findById(usuario3.getId())).thenReturn(usuario3);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/usuarios/3")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
