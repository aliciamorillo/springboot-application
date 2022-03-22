package edu.unisys.academy.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.unisys.academy.model.Alumno;
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

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAll() {

		return this.alumnoRepositorySpringData.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Alumno> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional 
	public Alumno save(Alumno alumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
