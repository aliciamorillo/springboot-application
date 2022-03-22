package edu.unisys.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.unisys.academy.service.AlumnoService;

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

//JSON: de texto a objeto: deserializar
//JSON: de objeto a texto: serializar

//GET http://localhost:8081/alumno/prueba

@RestController // El serv espera mensajes con el cuerpo JSON y devuelve mensajes con el cuerpo JSON
@RequestMapping("/alumno")
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("/prueba")
	public Alumno pruebaAlumno() {
		
		Alumno alumno = null; // para Hibernate: alumno: estado TRANSIENT
			alumno = new Alumno();
			alumno.setId(15L);
			alumno.setNombre("Alicia");
			alumno.setApellido("Morillo");
			alumno.setEdad(34);
		
		return alumno;
	}
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> leerTodos(){ //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findAll();
			responseEntity = ResponseEntity.ok(listaAlumnos); //ya estoy componiendo el HTTP de respuesta, indicando ok en la cabecera (200), y en el cuerpo, la lista de alumnos
			// La lista de alumnos se convierte en objeto json
			
		return responseEntity;
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
