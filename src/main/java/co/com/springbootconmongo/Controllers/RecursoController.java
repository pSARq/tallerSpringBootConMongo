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
}
