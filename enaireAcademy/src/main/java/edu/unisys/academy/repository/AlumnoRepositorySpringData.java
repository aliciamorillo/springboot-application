package edu.unisys.academy.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import edu.unisys.academy.model.Alumno;

//public interface AlumnoRepositorySpringData extends CrudRepository<Alumno, Long>{
//PagingAndSortingRepository hereda de CrudRepository, de tal manera que añade la posibilidad de consultar la base de datos por páginas (bloques, trozos) de un tamaño parametrizable

public interface AlumnoRepositorySpringData extends PagingAndSortingRepository<Alumno, Long> {
	
	//-- KEYWORD QUERIES
	// 1. Obtener un listado de alumnos que esténen un rango de edad
	public Iterable<Alumno> findByEdadBetween (int edadInicial, int edadFinal);
	
	//2. Obtener un listado de alumnos cuyo nombre contenga un patrón
	//hay que componer el patron en la capa de servicio (concatenando el caracter comodín manualmente para obtener el resultado esperado %)
	public Iterable<Alumno> findByNombreLike (String patron);
	
	//Esta búsqueda es literal si coincide o no con el nombre se recupera
	public Iterable<Alumno> findByNombre (String nombre); 
	
	
	//-- JPQL / NamedQueries
	//1. Obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
	@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	public Iterable<Alumno> busquedaPorNombreOApellido(String patron);
	
	
	//-- Nativas
	//1. VERSIÓN NATIVA: Obtener un listado de alumnos cuyo nombre o apellido coincida con un patrón
	@Query(value = "select * from alumnos a where a.nombre like %?1% or a.apellido like %?1%", nativeQuery = true)
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	
	//PROCEDIMIENTOS ALMACENADOS
	
	//@NamedQuery
	//Para referirnos a una NamedQuery desde Spring, sólo necesito un método que se llame igual
	public Iterable<Alumno> mayoresDe50();
	
	//PROCEDIMIENTOS ALMACENADOS
	@Procedure (name = "Alumno.alumnoRegistradosHoy")
	public Iterable<Alumno> procedimientoAlumnosAltaHoy();
	
	@Procedure (name = "Alumno.alumnosEdadMediaMinMax")
	public Map<String, Object> procedimientoAlumnosEstadisticasEdad(int edadmax, int edadmin, float edadmedia);
	
	@Procedure (name = "Alumno.alumnosNombreComo")
	public Iterable<Alumno> procedimientoAlumnosNombreComo(@Param("patron") String patron);	
	
}
