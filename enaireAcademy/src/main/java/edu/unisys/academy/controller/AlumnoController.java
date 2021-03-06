package edu.unisys.academy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	//GET http://localhost:8081/alumno/prueba
	
	@Autowired
	private AlumnoService alumnoService;
	
	Logger log = LoggerFactory.getLogger(AlumnoController.class);
	
	@GetMapping("/prueba")
	public Alumno pruebaAlumno() {
		
		Alumno alumno = null; // para Hibernate: alumno: estado TRANSIENT
			
			log.debug("Entrando en /prueba debug");
			log.info("Entrando en /prueba info");
			
			alumno = new Alumno(); //Aqu?? el alumno est?? en estado TRANSIENT: no existe
			alumno.setId(15L);
			alumno.setNombre("Alicia");
			alumno.setApellido("Morillo");
			alumno.setEdad(34);
		
		return alumno;
	}
	
	/**
	 * ********************************************
	 * INICIO DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> leerTodos(){ //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findAll();
			responseEntity = ResponseEntity.ok(listaAlumnos); //ya estoy componiendo el HTTP de respuesta, indicando ok en la cabecera (200), y en el cuerpo, la lista de alumnos
			// La lista de alumnos se convierte en objeto json
			
		return responseEntity;
	}
	
	@GetMapping("/mayoresDe50")
	public ResponseEntity<?> obtenerAlumnosMayoresDe50(){
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnosMasDe50 = null;
		
		listaAlumnosMasDe50 = this.alumnoService.mayoresDe50();
			responseEntity = ResponseEntity.ok(listaAlumnosMasDe50);
			
		return responseEntity;
	}
	
	@GetMapping("/buscarporrangoedad/{edad1}/{edad2}") //GET http://localhost:8081/alumno/6/90
	public ResponseEntity<?> obtenerAlumnosPorRangoEdad(@PathVariable int edad1, @PathVariable int edad2){
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findByEdadBetween(edad1, edad2);
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombrelike/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> obtenerAlumnosPorNombreLike(@PathVariable String patron){  
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findByNombreLike(patron);
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombre/{nombre}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> obtenerAlumnosPorRangoEdad(@PathVariable String nombre){  
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findByNombre(nombre);
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombreoape/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> busquedaAlumnosPorNombreoApe(@PathVariable String patron){  
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.busquedaPorNombreOApellido(patron);
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	@GetMapping("/buscarpornombreoapenativa/{patron}") //GET http://localhost:8081/alumno/Pepa
	public ResponseEntity<?> buscarAlumnosPorNombreoApeNativa (@PathVariable String patron){
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> lista_alumnos = null;
		
			lista_alumnos = this.alumnoService.busquedaPorNombreOApellidoNativa(patron);
			responseEntity = ResponseEntity.ok(lista_alumnos);
		
		return responseEntity;
	}
	
	@GetMapping("/pagina") //GET http://localhost:8081/alumno/pagina?page=0&size=2
	public ResponseEntity<?> listarAlumnosPorPagina (Pageable pageable) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> paginaAlumnos = null;
		
			paginaAlumnos = this.alumnoService.findAll(pageable);
			responseEntity = ResponseEntity.ok(paginaAlumnos);
		
		return responseEntity;
	}
	
	@GetMapping("/{id}") //GET http://localhost:8081/alumno/3
	public ResponseEntity<?> leerAlumnoPorId (@PathVariable Long id){
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> optionalAlumno = null;
		Alumno alumnoLeido = null;
		
			optionalAlumno = this.alumnoService.findById(id);
			
			if(optionalAlumno.isPresent()) {
				//El ID existe: tenemos un alumno le??do de la BD
				alumnoLeido = optionalAlumno.get();
				responseEntity = ResponseEntity.ok(alumnoLeido);
			} else {
				//El ID no existe
				responseEntity = ResponseEntity.noContent().build();
			}
			
		return responseEntity;
	}
	
	
	// @param: br: es la info de los errores detectados
	// @return: un mensaje de HTTP con los errores
	
	private ResponseEntity<?> obtenerErrores(BindingResult br){
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;
		
			listaErrores = br.getAllErrors();
			responseEntity = ResponseEntity.badRequest().body(listaErrores);
			
			listaErrores.forEach(objetoError -> {
				log.error(objetoError.toString());
			});
			
		return responseEntity;
	}
	
	@PostMapping //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumno (@Valid @RequestBody Alumno alumno, BindingResult br){
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoCreado = null;
		
			//Comprobar si tengo errores:
			if(br.hasErrors()) {
				log.error("El alumno trae errores - POST");
				responseEntity = obtenerErrores(br);
			} else {
				log.debug("El alumno pasa la validaci??n");
				alumnoCreado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoCreado); //201
			}
			
		return responseEntity;
	}
	
	@PostMapping("/crearConFoto") //POST http://localhost:8081/alumno/
	public ResponseEntity<?> insertarAlumnoConFoto (@Valid @RequestBody Alumno alumno, BindingResult br, MultipartFile archivo) throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoCreado = null;
		
			//Comprobar si tengo errores:
			//validarCP (alumno.cp) o en un FILTRO @WEBfilter
			if(br.hasErrors()) {
				log.error("El alumno trae errores - POST");
				responseEntity = obtenerErrores(br);
			} else {
				
				if(!archivo.isEmpty()) {
					try {
						alumno.setFoto(archivo.getBytes());
					} catch(IOException e) {
						e.printStackTrace();
						log.error("Error al acceder al contenido de la foto", e);
						throw e;
					}
				}
				
				log.debug("El alumno pasa la validaci??n");
				alumnoCreado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoCreado); //201
			}
			
		return responseEntity;
	}
	
	@PutMapping("/editarConFoto/{id}")
	public ResponseEntity<?> editarAlumnoConFoto (@Valid Alumno alumno, BindingResult br, @PathVariable Long id, MultipartFile archivo) throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoModificado = null;
		
		if(br.hasErrors()) {
			log.error("El alumno trae errores - POST");
			responseEntity = obtenerErrores(br);
		} else {
			//Antes de insertar: inspeccionar archivos
			if(!archivo.isEmpty()) {
				try {
					alumno.setFoto(archivo.getBytes());
				} catch(IOException e) {
					e.printStackTrace();
					log.error("Error al acceder al contenido de la foto", e);
					throw e;
				}
			}
			
			log.debug ("El alumno pasa la validaci??n");
			alumnoModificado = this.alumnoService.update(alumno, id);
			responseEntity = (alumnoModificado != null) ? (ResponseEntity.ok (alumnoModificado)) : (ResponseEntity.notFound().build());
		}
		
		return responseEntity;
	}
	
	@GetMapping("/obtenerfoto/{id}")
	public ResponseEntity<?> leerFotoAlumnoPorId (@PathVariable Long id){
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> opcionalAlumno = null;
		Alumno alumnoLeido = null;
		Resource fotoAlumno = null;
		
		opcionalAlumno = this.alumnoService.findById(id);
		
		if(opcionalAlumno.isPresent()) {
			alumnoLeido = opcionalAlumno.get();
			fotoAlumno = new ByteArrayResource(alumnoLeido.getFoto());
			responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fotoAlumno);
		}
		
		return responseEntity;
	}
	
	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumno (@Valid @RequestBody Alumno alumno, BindingResult br, @PathVariable Long id){
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoActualizado = null;
		
		if(br.hasErrors()) {
			log.error("El alumno trae errores - PUT");
			responseEntity = obtenerErrores(br);
		} else {
			alumnoActualizado = this.alumnoService.update(alumno, id);
				if(alumnoActualizado != null) {
					//Se ha modificado correctamente
					responseEntity = ResponseEntity.ok(alumnoActualizado);
				} else {
					responseEntity = ResponseEntity.notFound().build();
				}
		}
			
		return responseEntity;
	}
	
	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno/id
	public ResponseEntity<?> borrarAlumno (@PathVariable Long id){
		ResponseEntity<?> responseEntity = null;
			
			this.alumnoService.deleteById(id);
			responseEntity = ResponseEntity.ok().build();
	
		return responseEntity;
	}
	
	@GetMapping("/procedimientoAltasHoy") 
	public ResponseEntity<?> procedimientoAltasHoy(){ 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.procedimientoAlumnosAltaHoy();
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	@GetMapping("/procedimientoEstadisticasEdad") 
	public ResponseEntity<?> procedimientoEstadisticasEdad(){ 
		ResponseEntity<?> responseEntity = null;
		Map<String, Object> mapaEdades = null;
		
			mapaEdades = this.alumnoService.procedimientoAlumnosEstadisticasEdad();
			responseEntity = ResponseEntity.ok(mapaEdades);
			
		return responseEntity;
	}
	
	@GetMapping("/procedimientoBusquedaPorNombre/{patron}") 
	public ResponseEntity<?> procedimientoBusquedaPorNombre(@PathVariable String patron){ 
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.procedimientoAlumnosNombreComo(patron);
			responseEntity = ResponseEntity.ok(listaAlumnos);
			
		return responseEntity;
	}
	
	/**
	 * ********************************************
	 * FIN DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	/**
	 * **********************************************
	 * INICIO DE ACCESO A BASE DATOS CON HIBERNATE JPA
	 * **********************************************
	 */
	
	@GetMapping("/hbjpa") //GET http://localhost:8081/alumno/hbjpa
	public ResponseEntity<?> leerTodosHbJpa () { //con ResponseEntity<?> indico que devuelvo un mensaje HTTP y que el cuerpo lleva un tipo cualquiera (en JSON)
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;
		
			listaAlumnos = this.alumnoService.findAllHbJpa();
			responseEntity = ResponseEntity.ok(listaAlumnos);//ya estoy componinedo el HTTP de respuesta, indicando OK en la cabecera (200) y en el cuerpo, la lista de alumnos
			
		return responseEntity;
	}

	@GetMapping("/hbjpa/{id}") //GET http://localhost:8081/alumno//hbjpa/3
	public ResponseEntity<?> leerAlumnoPorIdHbJpa (@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoLeido = null;
		
			alumnoLeido = this.alumnoService.findByIdHbJpa(id);
			if (alumnoLeido != null) {
				//ese id exist??a y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.ok(alumnoLeido);
				
			} else {
				//TODO hacer el log
				//ese id exist??a y por tanto, tenemos un alumno que devolver
				responseEntity = ResponseEntity.noContent().build();
			}
		
		return responseEntity;
	}
	
	@PostMapping("/hbjpa") //POST http://localhost:8081/alumno/hbjpa
	public ResponseEntity<?> insertarAlumnoHbJpa (@RequestBody Alumno alumno) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoCreado = null;
		
			alumnoCreado = this.alumnoService.saveHbJpa(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoCreado);
		
		return responseEntity;
	}
	
	@PutMapping("/hbjpa/{id}") //PUT http://localhost:8081/alumno//hbjpa/id
	public ResponseEntity<?> modificarAlumnoHbJpa (@RequestBody Alumno alumno, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoActualizado = null;
		
			alumnoActualizado = this.alumnoService.updateHbJpa(alumno, id);
			if (alumnoActualizado != null) {
				//Se ha MODIFICADO correctamente
				responseEntity = ResponseEntity.ok(alumnoActualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		
		return responseEntity;
	}
	
	@DeleteMapping("/hbjpa/{id}") //DELETE http://localhost:8081/alumno/hbjpa/id
	public ResponseEntity<?> borrarAlumnoHbJpa (@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		
			this.alumnoService.deleteByIdHbJpa(id);
			responseEntity = ResponseEntity.ok().build();
		
		return responseEntity;
	}
	
	@GetMapping("/hbjpa/mayoresDe50") //GET http://localhost:8081/alumno/hbjpa/mayoresDe50
	public ResponseEntity<?> obtenerAlumnosMayoresDe50HbJpa (){
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listaAlumnosMasde50 = null;
		
		listaAlumnosMasde50 = this.alumnoService.mayoresDe50();
		responseEntity = ResponseEntity.ok(listaAlumnosMasde50);
		
		return responseEntity;
	}

	
	/**
	 * **********************************************
	 * FIN DE ACCESO A BASE DATOS CON HIBERNATE JPA
	 * **********************************************
	 */

}
