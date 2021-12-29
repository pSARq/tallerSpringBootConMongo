package co.com.springbootconmongo.Controllers;

import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Services.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biblioteca")
public class RecursoController {

    @Autowired
    RecursoService recursoService;

    @PostMapping("/crear")
    public ResponseEntity<RecursoDTO> crearRecurso(@RequestBody RecursoDTO dto){
        return new ResponseEntity(recursoService.crearRecurso(dto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<RecursoDTO> obtenerRecursos(){
        return new ResponseEntity(recursoService.obtenerRecursos(), HttpStatus.OK);
    }

    @PutMapping("/modificar")
    public ResponseEntity<RecursoDTO>  modificar(@RequestBody RecursoDTO dto){
        if (dto.getId() != null){
            return new ResponseEntity(recursoService.modificar(dto), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity borrar(@PathVariable("id") String id){
        try {
            recursoService.borrar(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
