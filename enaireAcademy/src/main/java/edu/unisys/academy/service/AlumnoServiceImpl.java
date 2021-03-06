package edu.unisys.academy.service;

import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unisys.academy.model.Alumno;
import edu.unisys.academy.repository.AlumnoRepositoryHibernateJPA;
import edu.unisys.academy.repository.AlumnoRepositorySpringData;


@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	/*
	 * Acceder BD: dos formas:
	 * - Antigua: Hibernate contexto de Persistencia
	 * - Moderna: Spring Data JPA
	 */
	
	@Autowired
	private AlumnoRepositorySpringData alumnoRepositorySpringData;
	
	@Autowired
	private AlumnoRepositoryHibernateJPA alumnoRepositoryHibernateJPA;

	
	/**
	 * ********************************************
	 * INICIO DE ACCESO A BASE DATOS CON SPRINGDATA
	 * ********************************************
	 */
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAll() {
		return this.alumnoRepositorySpringData.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Alumno> findById(Long id) {
		return this.alumnoRepositorySpringData.findById(id);
	}

	@Override
	@Transactional 
	public Alumno save(Alumno alumno) {
		return this.alumnoRepositorySpringData.save(alumno);
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		Alumno alumnoActualizado = null;
		Alumno alumnoLeido = null;
		Optional<Alumno> alumnoOptional = null;
		
		//1. Leer el alumno
		alumnoOptional = this.alumnoRepositorySpringData.findById(id);
		
		if(alumnoOptional.isPresent()) {
			alumnoLeido = alumnoOptional.get();
			
			//2. Modificar los atributos que desee
			alumnoLeido.setApellido(alumno.getApellido());
			alumnoLeido.setNombre(alumno.getNombre());
			alumnoLeido.setEmail(alumno.getEmail());
			alumnoLeido.setEdad(alumno.getEdad());
			
			if(alumno.getFoto() != null) {
				alumnoLeido.setFoto(alumno.getFoto());
			}
			
			//3. Hacer el save sobre el alumno modificado
			alumnoActualizado = this.alumnoRepositorySpringData.save(alumnoLeido);
		}
		
		return alumnoActualizado;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.alumnoRepositorySpringData.deleteById(id);
	}
		
	

	



	
	
	
	// CONSULTAS NO CRUD

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edadInicial, int edadFinal) {
		return this.alumnoRepositorySpringData.findByEdadBetween(edadInicial, edadFinal);
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByNombreLike(String patron) {
		return this.alumnoRepositorySpringData.findByNombreLike("%" + patron + "%"); //Esto funcionar??a igual que el findByNameContaining
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByNombre(String nombre) {
		return this.alumnoRepositorySpringData.findByNombre(nombre); //Esto funcionar??a igual que el findByNameContaining
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellido(String patron) {
		return this.alumnoRepositorySpringData.busquedaPorNombreOApellido(patron);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron) {
		return this.alumnoRepositorySpringData.busquedaPorNombreOApellidoNativa(patron);
	}

	@Override
	@Transactional (readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) {
		return this.alumnoRepositorySpringData.findAll(pageable);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> mayoresDe50() {
		// TODO Auto-generated method stub
		return this.alumnoRepositorySpringData.mayoresDe50();
	}
	
	@Override
	@Transactional //Cuando se llama a procedimientos relacionados: indicar ??nicamente Transactional, aunque no modifique ning??n registro
	public Iterable<Alumno> procedimientoAlumnosAltaHoy() {
		return this.alumnoRepositorySpringData.procedimientoAlumnosAltaHoy();
	}
	
	@Override
	@Transactional
	public Map<String, Object> procedimientoAlumnosEstadisticasEdad() {
		return this.alumnoRepositorySpringData.procedimientoAlumnosEstadisticasEdad(0,0,0f);
	}
	
	@Override
	@Transactional
	public Iterable<Alumno> procedimientoAlumnosNombreComo(String patron) {
		return this.alumnoRepositorySpringData.procedimientoAlumnosNombreComo("%"+patron+"%");
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

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAllHbJpa() {
		return this.alumnoRepositoryHibernateJPA.leerTodosLosAlumnos();
	}

	@Override
	@Transactional (readOnly = true)
	public Alumno findByIdHbJpa(Long id) {
		return this.alumnoRepositoryHibernateJPA.leerAlumnoPorId(id);
	}

	@Override
	@Transactional
	public Alumno saveHbJpa(Alumno alumno) {
		return this.alumnoRepositoryHibernateJPA.crearAlumno(alumno);
	}

	@Override
	public Alumno updateHbJpa(Alumno alumno, Long id) {
		Alumno alumnoActualizado = null;
		Alumno alumnoLeido = null;
		
		//1. Leer Alumno
		alumnoLeido = this.alumnoRepositoryHibernateJPA.leerAlumnoPorId(id);
		
		if(alumnoLeido != null) {
			//2. Modificar los atributos que desee
			alumnoLeido.setApellido(alumno.getApellido());
			alumnoLeido.setNombre(alumno.getNombre());
			alumnoLeido.setEmail(alumno.getEmail());
			alumnoLeido.setEdad(alumno.getEdad());
			
			//3. SAVE sobre el alumno modificado
			alumnoActualizado = this.alumnoRepositoryHibernateJPA.actualizarAlumno(alumnoLeido);
		}
		
		return alumnoActualizado;
	}

	@Override
	@Transactional
	public void deleteByIdHbJpa(Long id) {
		this.alumnoRepositoryHibernateJPA.borrarAlumno(id);
		
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> mayoresDe50HbJpa() {
		return this.alumnoRepositoryHibernateJPA.mayoresDe50();
	}
	
	/**
	 * ********************************************
	 * FIN DE ACCESO A BASE DATOS CON HIBERNATEJPA
	 * ********************************************
	 */

}
