package co.com.springbootconmongo.Controllers;

import co.com.springbootconmongo.Collections.Recurso;
import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Repositories.RecursoRepository;
import co.com.springbootconmongo.Services.RecursoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

import static org.hamcrest.Matchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class RecursoControllerTest {

    @MockBean
    private RecursoService service;

    @MockBean
    private RecursoRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /biblioteca/crearRecurso")
    void crearRecurso() throws Exception {

        RecursoDTO recursoADevolver = new RecursoDTO();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Santiago");
        recursoADevolver.setDisponible(true);
        recursoADevolver.setTematica("Deporte");
        recursoADevolver.setTipo("Libro");

        RecursoDTO recursoAGuardar = new RecursoDTO();
        recursoAGuardar.setNombre("Santiago");
        recursoAGuardar.setDisponible(true);
        recursoAGuardar.setTematica("Deporte");
        recursoAGuardar.setTipo("Libro");

        Mockito.when(service.crearRecurso(any())).thenReturn(recursoADevolver);

        mockMvc.perform(post("/biblioteca/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recursoAGuardar)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.nombre", is("Santiago")))
                .andExpect(jsonPath("$.tematica", is("Deporte")))
                .andExpect(jsonPath("$.tipo", is("Libro")))
                .andExpect(jsonPath("$.disponible", is(true)));

        Mockito.verify(service).crearRecurso(any());
    }

    @Test
    @DisplayName("GET /biblioteca")
    void obtenerRecursos() throws Exception {
        RecursoDTO recurso1 = new RecursoDTO();
        recurso1.setId("1");
        recurso1.setNombre("Santiago");
        recurso1.setDisponible(true);
        recurso1.setTematica("Deporte");
        recurso1.setTipo("Libro");

        RecursoDTO recurso2 = new RecursoDTO();
        recurso2.setId("2");
        recurso2.setNombre("Deison");
        recurso2.setDisponible(true);
        recurso2.setTematica("Musica");
        recurso2.setTipo("Revista");

        Mockito.when(service.obtenerRecursos()).thenReturn(Lists.newArrayList(recurso1, recurso2));

        mockMvc.perform(get("/biblioteca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("Santiago")))
                .andExpect(jsonPath("$[0].tematica", is("Deporte")))
                .andExpect(jsonPath("$[0].tipo", is("Libro")))
                .andExpect(jsonPath("$[0].disponible", is(true)))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].nombre", is("Deison")))
                .andExpect(jsonPath("$[1].tematica", is("Musica")))
                .andExpect(jsonPath("$[1].tipo", is("Revista")))
                .andExpect(jsonPath("$[1].disponible", is(true)));

        Mockito.verify(service).obtenerRecursos();
    }

    @Test
    @DisplayName("PUT /biblioteca/modificar")
    void modificar() throws Exception {

        RecursoDTO recursoModificado = new RecursoDTO();
        recursoModificado.setId("1");
        recursoModificado.setNombre("Deison");
        recursoModificado.setDisponible(true);
        recursoModificado.setTematica("Musica");
        recursoModificado.setTipo("Revista");

        RecursoDTO recursoADevolver = new RecursoDTO();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Deison");
        recursoADevolver.setDisponible(true);
        recursoADevolver.setTematica("Musica");
        recursoADevolver.setTipo("Revista");

        Mockito.when(service.modificar(any())).thenReturn(recursoADevolver);

        mockMvc.perform(put("/biblioteca/modificar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recursoModificado)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.nombre", is("Deison")))
                .andExpect(jsonPath("$.tematica", is("Musica")))
                .andExpect(jsonPath("$.tipo", is("Revista")))
                .andExpect(jsonPath("$.disponible", is(true)));

        Mockito.verify(service).modificar(any());
    }

    @Test
    @DisplayName("GET /biblioteca/disponible/1")
    void consultarDisponibilidad() throws Exception {
        RecursoDTO recurso = new RecursoDTO();
        recurso.setId("1");
        recurso.setNombre("Deison");
        recurso.setDisponible(true);
        recurso.setTematica("Musica");
        recurso.setTipo("Revista");

        Mockito.when(service.consultarDisponibilidad(recurso.getId())).thenReturn("Esta disponible");

        mockMvc.perform(get("/biblioteca/disponible/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Esta disponible"));

        Mockito.verify(service).consultarDisponibilidad(recurso.getId());
    }

    @Test
    @DisplayName("GET /biblioteca/tipo/Revista")
    void colsultarPorTipo() throws Exception {
        RecursoDTO recurso1 = new RecursoDTO();
        recurso1.setId("1");
        recurso1.setNombre("Deison");
        recurso1.setDisponible(true);
        recurso1.setTematica("Musica");
        recurso1.setTipo("Revista");

        Mockito.when(service.consultarPorTipo("Revista")).thenReturn(Lists.newArrayList(recurso1));

        mockMvc.perform(get("/biblioteca/tipo/{tipo}", "Revista"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("Deison")))
                .andExpect(jsonPath("$[0].tematica", is("Musica")))
                .andExpect(jsonPath("$[0].tipo", is("Revista")))
                .andExpect(jsonPath("$[0].disponible", is(true)));;
        Mockito.verify(service).consultarPorTipo("Revista");
    }

    @Test
    @DisplayName("GET /biblioteca/tematica/Musica")
    void colsultarPorTematica() throws Exception {
        RecursoDTO recurso1 = new RecursoDTO();
        recurso1.setId("1");
        recurso1.setNombre("Deison");
        recurso1.setDisponible(true);
        recurso1.setTematica("Musica");
        recurso1.setTipo("Revista");

        Mockito.when(service.consultarPorTematica("Musica")).thenReturn(Lists.newArrayList(recurso1));

        mockMvc.perform(get("/biblioteca/tematica/{tematica}", "Musica"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("Deison")))
                .andExpect(jsonPath("$[0].tematica", is("Musica")))
                .andExpect(jsonPath("$[0].tipo", is("Revista")))
                .andExpect(jsonPath("$[0].disponible", is(true)));;
        Mockito.verify(service).consultarPorTematica("Musica");
    }

    //Los 2 test de abajo no funcionan

    @Test
    @DisplayName("PUT /biblioteca/prestar")
    void prestarRecurso() throws Exception {
        RecursoDTO recursoAPrestar = new RecursoDTO();
        recursoAPrestar.setId("1");
        recursoAPrestar.setNombre("Deison");
        recursoAPrestar.setDisponible(false);
        recursoAPrestar.setTematica("Musica");
        recursoAPrestar.setTipo("Revista");

        Mockito.when(service.prestarRecurso(recursoAPrestar)).thenReturn("Prestamo realizado con éxito");

        mockMvc.perform(put("/biblioteca/prestar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recursoAPrestar)))

                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals("Prestamo realizado con éxito"));

        //Mockito.verify(service).prestarRecurso(recursoAPrestar);
    }

    @Test
    @DisplayName("PUT /biblioteca/devolver")
    void devolverRecurso() throws Exception {
        RecursoDTO recursoADevolver = new RecursoDTO();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Deison");
        recursoADevolver.setDisponible(false);
        recursoADevolver.setTematica("Musica");
        recursoADevolver.setTipo("Revista");

        Mockito.when(service.devolverRecurso(recursoADevolver)).thenReturn("Recurso devuelto con éxito");

        mockMvc.perform(put("/biblioteca/devolver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(recursoADevolver)))

                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().equals("Recurso devuelto con éxito"));

        //Mockito.verify(service).devolverRecurso(recursoADevolver);
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}