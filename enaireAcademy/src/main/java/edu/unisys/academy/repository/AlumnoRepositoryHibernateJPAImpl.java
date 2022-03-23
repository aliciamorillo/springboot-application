package edu.unisys.academy.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import edu.unisys.academy.model.Alumno;

@Repository
public class AlumnoRepositoryHibernateJPAImpl implements AlumnoRepositoryHibernateJPA {

	@PersistenceContext
	EntityManager entityManager;
	
	/* JPAs
	 * find (class, pk) - leo
	 * merge (entity) - actualizo
	 * persist (entity) - inserto
	 * remove (entity) - elimino
	 */
	
	@Override
	public Alumno leerAlumnoPorId(Long id) {
		return this.entityManager.find(Alumno.class, id);
	}

	@Override
	public Alumno crearAlumnoPorId(Alumno alumno) {
		this.entityManager.persist(alumno);
		this.entityManager.flush(); //Fuerzo/sintonizo el contexto de persistencia con la BD
		return alumno; //Con esa op consigo que el alumno tenga su id
	}

	@Override
	public List<Alumno> leerTodosLosAlumnos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno actualizarAlumno(Alumno alumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarAlumno(Long id) {
		// TODO Auto-generated method stub
		
	}

}
