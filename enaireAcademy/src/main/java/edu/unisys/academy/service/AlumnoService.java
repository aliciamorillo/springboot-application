package edu.unisys.academy.service;

import java.util.Optional;

import edu.unisys.academy.model.Alumno;

// Describir operaciones que se pueden realizar con el alumno
public interface AlumnoService {
	
	public Iterable<Alumno> findAll();
	
	public Optional<Alumno> findById(Long id);
	
	public Alumno save(Alumno alumno);
	
	public Alumno update(Alumno alumno, Long id);
	
	public void deleteById (Long id);

}