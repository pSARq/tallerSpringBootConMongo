package co.com.springbootconmongo.Services;

import co.com.springbootconmongo.Collections.Recurso;
import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Mappers.RecursoMapper;
import co.com.springbootconmongo.Repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository repository;
    RecursoMapper mapper = new RecursoMapper();

    public RecursoDTO crearRecurso(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        return mapper.fromCollection(repository.save(recurso));
    }

    public List<RecursoDTO> obtenerRecursos(){
        List<Recurso> recursos = (List<Recurso>) repository.findAll();
        return mapper.fromCollectionList(recursos);
    }

    public RecursoDTO modificar(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        repository.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return mapper.fromCollection(repository.save(recurso));
    }

    public void borrar(String id){
        repository.deleteById(id);
    }

    public String consultarDisponibilidad(String id){
        Recurso elemento = repository.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        if (elemento.isDisponible()){
            return "Esta disponible";
        }
        return elemento.getFechaPrestamo();
    }

    public String prestarRecurso(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        Recurso elemento = repository.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        if (elemento.isDisponible()){
            recurso.setFechaPrestamo(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            recurso.setDisponible(false);
            repository.save(recurso);
            return "Prestamo realizado con éxito";
        }
        return "El recurso no se encuentra disponible en este momento";
    }

    public String devolverRecurso(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        Recurso elemento = repository.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        if (elemento.isDisponible() == false){
            recurso.setFechaPrestamo(null);
            recurso.setDisponible(true);
            repository.save(recurso);
            return "Recurso devuelto con éxito";
        }
        return "El recurso no ha sido prestado";
    }

    public List<RecursoDTO> consultarPorTematica(String tematica){
        List<Recurso> recursos = (List<Recurso>) repository.findByTematicaIn(tematica);
        return mapper.fromCollectionList(recursos);
    }

    public List<RecursoDTO> consultarPorTipo(String tipo){
        List<Recurso> recursos = (List<Recurso>) repository.findByTipoIn(tipo);
        return mapper.fromCollectionList(recursos);
    }

}
