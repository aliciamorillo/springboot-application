package edu.unisys.academy.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.unisys.academy.model.Curso;
import edu.unisys.academy.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    
    Logger log = LoggerFactory.getLogger(CursoController.class);

    @GetMapping
    public ResponseEntity<?> listarCursos() {
        ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
        Iterable<Curso> ita = null;

        log.debug("Entrando en listarCursos()");


        ita = this.cursoService.findAll();
        log.debug("Lista recuperada = " + ita);
        responseEntity = ResponseEntity.ok(ita);
        log.debug("Saliendo de listarCursos()");

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarCursoPorId(@PathVariable Long id) {
        ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
        Optional<Curso> opa = null;

        opa = this.cursoService.findById(id);
        if (opa.isPresent()) {
            Curso Curso_leido = opa.get();
            responseEntity = ResponseEntity.ok(Curso_leido);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }

        return responseEntity;

    }

    @PostMapping
    public ResponseEntity<?> insertarCurso(@RequestBody Curso Curso) {
        ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
        Curso cursoCreado = null;

        log.debug("Sin errores en la entrada");
        cursoCreado = this.cursoService.save(Curso);
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(cursoCreado);

        return responseEntity;

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarCurso(@RequestBody Curso Curso, @PathVariable Long id) {
        ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
        Curso cursoActualizado = null;

        cursoActualizado = this.cursoService.update(Curso, id);
        if (cursoActualizado != null) {
            responseEntity = ResponseEntity.ok(cursoActualizado);
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }

        return responseEntity;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarCurso(@PathVariable Long id) {
        ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta

        this.cursoService.deleteById(id);
        responseEntity = ResponseEntity.ok().build();
        // TODO mejorar la respuesta ante un ID que no exista
        // la DOC no concuerda pero nos lanza un
        // org.springframework.dao.EmptyResultDataAccessException: No class
        // entity with id 20 exists!

        return responseEntity;

    }

}


