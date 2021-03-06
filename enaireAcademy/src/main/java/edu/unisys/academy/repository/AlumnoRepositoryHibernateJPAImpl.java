package edu.unisys.academy.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	public Alumno crearAlumno(Alumno a) {
		this.entityManager.persist(a);
		this.entityManager.flush(); //Fuerzo/sintonizo el contexto de persistencia con la BD
		return a; //Con esa op consigo que el alumno tenga su id
	}

	@Override
	public List<Alumno> leerTodosLosAlumnos() {
		List<Alumno> listaAlumnos = null;
		//No existe el findAll en el entityManager, tenemos que hacer la consulta a mano
		//Query query = entityManager.createQuery("select a from Alumno a"); //a = alias
		//listaAlumnos = (List<Alumno>)query.getResultList();
		
		TypedQuery<Alumno> typedQuery = entityManager.createQuery("select a from Alumno a", Alumno.class);
		listaAlumnos = typedQuery.getResultList();
		
		return listaAlumnos;
	}

	@Override
	public Alumno actualizarAlumno(Alumno a) {
		this.entityManager.merge(a);
		//this.entityManager.flush(); //si incluyera una modificación del ID, sería necesario
		return a;
	}

	@Override
	public void borrarAlumno(Long id) {
		Alumno alumnoEliminar = this.leerAlumnoPorId(id); //this.entityManager.find(Alumno.class, id);
		//TODO opcion mejora, comprobar que es !=null, que ese registro existe
		this.entityManager.remove(alumnoEliminar);
		
	}

	@Override
	public Iterable<Alumno> mayoresDe50() {
		Query query = this.entityManager.createNamedQuery("Alumno.mayoresDe50");
		return query.getResultList();
	}

}
