package co.com.springbootconmongo.Repositories;

import co.com.springbootconmongo.Collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecursoRepository extends MongoRepository<Recurso, String> {
    List<Recurso> findByTematicaIn(String tematica);
}
