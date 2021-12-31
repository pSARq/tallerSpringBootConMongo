package co.com.springbootconmongo.Services;

import co.com.springbootconmongo.Collections.Recurso;
import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Repositories.RecursoRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RecursoServiceTest {

    @MockBean
    private RecursoRepository repository;

    @Autowired
    private RecursoService service;

    @Test
    void crearRecurso() {
        RecursoDTO recursoAGuardar = new RecursoDTO();
        recursoAGuardar.setNombre("Santiago");
        recursoAGuardar.setDisponible(true);
        recursoAGuardar.setTematica("Deporte");
        recursoAGuardar.setTipo("Libro");

        Recurso recursoADevolver = new Recurso();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Santiago");
        recursoADevolver.setDisponible(true);
        recursoADevolver.setTematica("Deporte");
        recursoADevolver.setTipo("Libro");

        Mockito.when(repository.save(any())).thenReturn(recursoADevolver);

        RecursoDTO respuesta = service.crearRecurso(recursoAGuardar);

        Assertions.assertNotNull(respuesta, "el valor guardado no debe ser nulo");
        Assertions.assertEquals(recursoADevolver.getNombre(), respuesta.getNombre(), "El nombre no corresponde");
        Assertions.assertEquals(recursoADevolver.isDisponible(), respuesta.isDisponible(), "La disponibilidad no es igual");
        Assertions.assertEquals(recursoADevolver.getTematica(), respuesta.getTematica(), "La tematica no es igual");
        Assertions.assertEquals(recursoADevolver.getTipo(), respuesta.getTipo(), "El tipo no es igual");

        Mockito.verify(repository).save(any());
    }

    @Test
    void obtenerRecursos() {
        Recurso recurso1 = new Recurso();
        recurso1.setId("1");
        recurso1.setNombre("Santiago");
        recurso1.setDisponible(true);
        recurso1.setTematica("Deporte");
        recurso1.setTipo("Libro");

        Recurso recurso2 = new Recurso();
        recurso2.setId("2");
        recurso2.setNombre("Deison");
        recurso2.setDisponible(true);
        recurso2.setTematica("Musica");
        recurso2.setTipo("Revista");

        Mockito.when(repository.findAll()).thenReturn(Lists.newArrayList(recurso1, recurso2));

        List<RecursoDTO> respuesta = service.obtenerRecursos();

        Assertions.assertEquals(2, respuesta.size());
        Assertions.assertEquals(recurso1.getId(), respuesta.get(0).getId());
        Assertions.assertEquals(recurso1.getNombre(), respuesta.get(0).getNombre());
        Assertions.assertEquals(recurso1.isDisponible(), respuesta.get(0).isDisponible());
        Assertions.assertEquals(recurso1.getTematica(), respuesta.get(0).getTematica());
        Assertions.assertEquals(recurso1.getTipo(), respuesta.get(0).getTipo());
        Assertions.assertEquals(recurso2.getId(), respuesta.get(1).getId());
        Assertions.assertEquals(recurso2.getNombre(), respuesta.get(1).getNombre());
        Assertions.assertEquals(recurso2.isDisponible(), respuesta.get(1).isDisponible());
        Assertions.assertEquals(recurso2.getTematica(), respuesta.get(1).getTematica());
        Assertions.assertEquals(recurso2.getTipo(), respuesta.get(1).getTipo());

        Mockito.verify(repository).findAll();
    }

    @Test
    void modificar() {
        RecursoDTO recursoModificado = new RecursoDTO();
        recursoModificado.setId("1");
        recursoModificado.setNombre("Deison");
        recursoModificado.setDisponible(true);
        recursoModificado.setTematica("Musica");
        recursoModificado.setTipo("Revista");

        Recurso recursoADevolver = new Recurso();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Deison");
        recursoADevolver.setDisponible(true);
        recursoADevolver.setTematica("Musica");
        recursoADevolver.setTipo("Revista");

        Mockito.when(repository.save(any())).thenReturn(recursoADevolver);
        Mockito.when(repository.findById(recursoADevolver.getId())).thenReturn(Optional.of(recursoADevolver));

        RecursoDTO respuesta = service.modificar(recursoModificado);

        Assertions.assertEquals(recursoADevolver.getId(), respuesta.getId());
        Assertions.assertEquals(recursoADevolver.getNombre(), respuesta.getNombre());
        Assertions.assertEquals(recursoADevolver.isDisponible(), respuesta.isDisponible());
        Assertions.assertEquals(recursoADevolver.getTematica(), respuesta.getTematica());
        Assertions.assertEquals(recursoADevolver.getTipo(), respuesta.getTipo());

        Mockito.verify(repository).save(any());
        Mockito.verify(repository).findById(recursoADevolver.getId());
    }

    @Test
    void consultarDisponibilidad() {
        Recurso recurso = new Recurso();
        recurso.setId("1");
        recurso.setNombre("Deison");
        recurso.setDisponible(true);
        recurso.setTematica("Musica");
        recurso.setTipo("Revista");

        Mockito.when(repository.findById(recurso.getId())).thenReturn(Optional.of(recurso));

        String respuesta = service.consultarDisponibilidad(recurso.getId());

        Assertions.assertEquals("Esta disponible", respuesta);

        Mockito.verify(repository).findById(recurso.getId());
    }

    @Test
    void prestarRecurso() {
        RecursoDTO recursoAPrestar = new RecursoDTO();
        recursoAPrestar.setId("1");
        recursoAPrestar.setNombre("Deison");
        recursoAPrestar.setDisponible(false);
        recursoAPrestar.setTematica("Musica");
        recursoAPrestar.setTipo("Revista");

        Recurso recursoAlmacenado = new Recurso();
        recursoAlmacenado.setId("1");
        recursoAlmacenado.setNombre("Deison");
        recursoAlmacenado.setDisponible(true);
        recursoAlmacenado.setTematica("Musica");
        recursoAlmacenado.setTipo("Revista");

        Mockito.when(repository.findById(recursoAPrestar.getId())).thenReturn(Optional.of(recursoAlmacenado));

        String respuesta = service.prestarRecurso(recursoAPrestar);

        Assertions.assertEquals("Prestamo realizado con éxito", respuesta);

        Mockito.verify(repository).findById(recursoAPrestar.getId());
    }

    @Test
    void devolverRecurso() {
        RecursoDTO recursoADevolver = new RecursoDTO();
        recursoADevolver.setId("1");
        recursoADevolver.setNombre("Deison");
        recursoADevolver.setDisponible(true);
        recursoADevolver.setTematica("Musica");
        recursoADevolver.setTipo("Revista");

        Recurso recursoAlmacenado = new Recurso();
        recursoAlmacenado.setId("1");
        recursoAlmacenado.setNombre("Deison");
        recursoAlmacenado.setDisponible(false);
        recursoAlmacenado.setTematica("Musica");
        recursoAlmacenado.setTipo("Revista");

        Mockito.when(repository.findById(recursoADevolver.getId())).thenReturn(Optional.of(recursoAlmacenado));

        String respuesta = service.devolverRecurso(recursoADevolver);

        Assertions.assertEquals("Recurso devuelto con éxito", respuesta);

        Mockito.verify(repository).findById(recursoADevolver.getId());
    }

    @Test
    void consultarPorTematica() {
        Recurso recurso1 = new Recurso();
        recurso1.setId("1");
        recurso1.setNombre("Deison");
        recurso1.setDisponible(true);
        recurso1.setTematica("Musica");
        recurso1.setTipo("Revista");

        Recurso recurso2 = new Recurso();
        recurso2.setId("2");
        recurso2.setNombre("Santiago");
        recurso2.setDisponible(true);
        recurso2.setTematica("Musica");
        recurso2.setTipo("Revista");

        Mockito.when(repository.findByTematicaIn("Musica")).thenReturn(Lists.newArrayList(recurso1, recurso2));

        List<RecursoDTO> respuesta = service.consultarPorTematica("Musica");

        Assertions.assertEquals(2, respuesta.size());
        Assertions.assertEquals(recurso1.getId(), respuesta.get(0).getId());
        Assertions.assertEquals(recurso1.getNombre(), respuesta.get(0).getNombre());
        Assertions.assertEquals(recurso1.isDisponible(), respuesta.get(0).isDisponible());
        Assertions.assertEquals(recurso1.getTematica(), respuesta.get(0).getTematica());
        Assertions.assertEquals(recurso1.getTipo(), respuesta.get(0).getTipo());
        Assertions.assertEquals(recurso2.getId(), respuesta.get(1).getId());
        Assertions.assertEquals(recurso2.getNombre(), respuesta.get(1).getNombre());
        Assertions.assertEquals(recurso2.isDisponible(), respuesta.get(1).isDisponible());
        Assertions.assertEquals(recurso2.getTematica(), respuesta.get(1).getTematica());
        Assertions.assertEquals(recurso2.getTipo(), respuesta.get(1).getTipo());

        Mockito.verify(repository).findByTematicaIn("Musica");
    }

    @Test
    void consultarPorTipo() {
        Recurso recurso1 = new Recurso();
        recurso1.setId("1");
        recurso1.setNombre("Deison");
        recurso1.setDisponible(true);
        recurso1.setTematica("Musica");
        recurso1.setTipo("Revista");

        Recurso recurso2 = new Recurso();
        recurso2.setId("2");
        recurso2.setNombre("Santiago");
        recurso2.setDisponible(true);
        recurso2.setTematica("Musica");
        recurso2.setTipo("Revista");

        Mockito.when(repository.findByTipoIn("Revista")).thenReturn(Lists.newArrayList(recurso1, recurso2));

        List<RecursoDTO> respuesta = service.consultarPorTipo("Revista");

        Assertions.assertEquals(2, respuesta.size());
        Assertions.assertEquals(recurso1.getId(), respuesta.get(0).getId());
        Assertions.assertEquals(recurso1.getNombre(), respuesta.get(0).getNombre());
        Assertions.assertEquals(recurso1.isDisponible(), respuesta.get(0).isDisponible());
        Assertions.assertEquals(recurso1.getTematica(), respuesta.get(0).getTematica());
        Assertions.assertEquals(recurso1.getTipo(), respuesta.get(0).getTipo());
        Assertions.assertEquals(recurso2.getId(), respuesta.get(1).getId());
        Assertions.assertEquals(recurso2.getNombre(), respuesta.get(1).getNombre());
        Assertions.assertEquals(recurso2.isDisponible(), respuesta.get(1).isDisponible());
        Assertions.assertEquals(recurso2.getTematica(), respuesta.get(1).getTematica());
        Assertions.assertEquals(recurso2.getTipo(), respuesta.get(1).getTipo());

        Mockito.verify(repository).findByTipoIn("Revista");
    }
}