package co.com.springbootconmongo.Services;

import co.com.springbootconmongo.Collections.Recurso;
import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Mappers.RecursoMapper;
import co.com.springbootconmongo.Repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository repository;
    RecursoMapper mapper = new RecursoMapper();

    public RecursoDTO crearRecurso(RecursoDTO dto){
        Recurso recurso = mapper.fromDTO(dto);
        return mapper.fromCollection(repository.save(recurso));
    }

    public RecursoDTO obtenerRecursos(RecursoDTO dto){
        
    }

}
