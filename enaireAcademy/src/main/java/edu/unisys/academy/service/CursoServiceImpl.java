package edu.unisys.academy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unisys.academy.model.Alumno;
import edu.unisys.academy.model.Curso;
import edu.unisys.academy.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;

	 	@Override
	 	@Transactional (readOnly = true)
	    public Iterable<Curso> findAll() {
	        return this.cursoRepository.findAll();
	    }

	    @Override
	    public Optional<Curso> findById(Long id) {
	        Optional<Curso> opc = null;

	            opc = this.cursoRepository.findById(id);

	        return opc;
	    }

	    @Override
	    public Curso save(Curso curso) {
	        Curso curso_nuevo = null;
	        
	            curso_nuevo = this.cursoRepository.save(curso);
	    
	        return curso_nuevo;
	    }

	    @Override
	    public void deleteById(Long id) {
	        this.cursoRepository.deleteById(id);

	    }

	    @Override
	    @Transactional
	    public Curso update(Curso curso, Long id) {
	        Curso curso_actualizado = null;
	        Curso curso_leido = null;
	        Optional<Curso> opcurso = null;
	            
	             opcurso = this.cursoRepository.findById(id);
	             if (opcurso.isPresent())
	             {
	                 curso_leido = opcurso.get();
	                 curso_leido.setNombre(curso.getNombre());
	                 
	                 curso_actualizado =  this.cursoRepository.save(curso_leido);
	             }
	        
	        return curso_actualizado;
	    }

	    @Override
	    public Optional<Curso> asignarAlumnos(List<Alumno> alumnos, Long id) {
	        // TODO Auto-generated method stub
	        return null;
	    }

	}


