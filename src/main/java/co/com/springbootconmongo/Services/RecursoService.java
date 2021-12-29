package co.com.springbootconmongo.Services;

import co.com.springbootconmongo.Collections.Recurso;
import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Mappers.RecursoMapper;
import co.com.springbootconmongo.Repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    /*
    public String consultarDisponibilidad(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        Recurso elemento = repository.findById(recurso.getId()).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        if (elemento.isDisponible(){
            return "Esta disponible";
        }
        return elemento.getFechaPrestamo();
    }
     */

    public String consultarDisponibilidad(String id){
        Recurso elemento = repository.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        if (elemento.isDisponible()){
            return "Esta disponible";
        }
        return elemento.getFechaPrestamo();
    }

}
