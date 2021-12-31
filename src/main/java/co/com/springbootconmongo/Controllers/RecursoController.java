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

    @GetMapping("/disponible/{id}")
    public ResponseEntity<String> consultarDisponibilidad(@PathVariable("id") String id){
        if (id != null){
            return new ResponseEntity(recursoService.consultarDisponibilidad(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/prestar")
    public ResponseEntity<String>  prestarRecurso(@RequestBody RecursoDTO dto){
        if (dto.getId() != null){
            return new ResponseEntity(recursoService.prestarRecurso(dto), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/devolver")
    public ResponseEntity<String> devolverRecurso(@RequestBody RecursoDTO dto){
        if (dto.getId() != null){
            return new ResponseEntity(recursoService.devolverRecurso(dto), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tematica/{tematica}")
    public ResponseEntity<RecursoDTO> colsultarPorTematica(@PathVariable("tematica") String tematica){
        if (tematica != null){
            return new ResponseEntity(recursoService.consultarPorTematica(tematica), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<RecursoDTO> colsultarPorTipo(@PathVariable("tipo") String tipo){
        if (tipo != null){
            return new ResponseEntity(recursoService.consultarPorTipo(tipo), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
