package edu.unisys.academy.service;

import java.util.List;
import java.util.Optional;

import edu.unisys.academy.model.Alumno;
import edu.unisys.academy.model.Curso;

public interface CursoService {
	
    public Iterable<Curso> findAll();
    
    public Optional<Curso> findById (Long id);
    
    public Curso save (Curso curso);
    
    public void deleteById (Long id);
    
    public Curso update (Curso curso, Long id);
    
    //NO CRUD
    public Optional<Curso> asignarAlumnos (List<Alumno> alumnos, Long id);

}
