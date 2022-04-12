package edu.unisys.academy.repository;

import java.util.List;
import edu.unisys.academy.model.Alumno;

public interface AlumnoRepositoryHibernateJPA {
	
	Alumno leerAlumnoPorId(Long id);
	
	Alumno crearAlumno(Alumno a);
	
	List<Alumno> leerTodosLosAlumnos();
	
	Alumno actualizarAlumno(Alumno a);
	
	void borrarAlumno(Long id);
	
	public Iterable<Alumno> mayoresDe50();
	
}
