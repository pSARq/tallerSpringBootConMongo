package co.com.springbootconmongo.Repositories;

import co.com.springbootconmongo.Collections.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecursoRepository extends MongoRepository<Recurso, String> {

}
