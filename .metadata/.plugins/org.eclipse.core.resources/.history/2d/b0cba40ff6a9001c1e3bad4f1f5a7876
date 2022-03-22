package edu.unisys.academy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.unisys.academy.model.Alumno;

/*
 * RECIBO Y RESPONDO AL USUARIO HTTP
 * - POSTMAN -
 * 
 * API REST sobre Alumnos
 * - Leer todos los alumnos
 * - Leer un alumno
 * - Modificar un alumno
 * - Borrar un alumno
 * 
 */

//GET http://localhost:8081/alumno/prueba

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
	
	@GetMapping("/prueba")
	public Alumno pruebaAlumno() {
		
		Alumno alumno = null; // para Hibernate: alumno: estado TRANSIENT
		
			alumno.setId(15L);
			alumno.setNombre("Alicia");
			alumno.setApellido("Morillo");
			alumno.setEdad(34);
		
		return alumno;
	}
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> leerTodos(){
		return null;
	}
	
	@GetMapping("/{id}") //GET http://localhost:8081/alumno/3
	public ResponseEntity<?> leerAlumnoPorId (@PathVariable Long id){
		return null;
	}
	
	@PostMapping //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumno (@RequestBody Alumno alumno){
		return null;
	}
	
	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumno (@RequestBody Alumno alumno, @PathVariable Long id){
		return null;
	}
	
	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno/id
	public ResponseEntity<?> borrarAlumno (@PathVariable Long id){
		return null;
	}

}
