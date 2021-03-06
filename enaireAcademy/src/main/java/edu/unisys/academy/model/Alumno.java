package edu.unisys.academy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;//USANDO JPA
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.PrePersist;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity //Con esta anotación, le digo a Hibernate, que esto es una clase de java asociada a una tabla
@Table(name = "alumnos") //Nombre tabla en SQL - BBDD
@NamedQuery(name = "Alumno.mayoresDe50", query="SELECT a FROM Alumno a WHERE a.edad>50")
@NamedStoredProcedureQueries(
		{
			//POR CADA PROCEDIMIENTO ALMACENADO, DEBO CREAR UNA ANOTACIÓN @NAMEDSTOREPROCEDUREQUERY
			//PARA CADA UNA, DEBO INDICAR EL NAME (NOMBRE DESDE JPA) Y EL PROCEDURENAME (NOMBRE EN LA BD)
				//DENTRO DE CADA NAMEDPROCEDUREQUERY
					//TENGO QUE INDENTIFICAR LOS PARÁMETROS DE ENTRADA Y SALIDA CORRESPONDIENTES CON @STOREPROCEDUREPARAMETER
					//(en el caso de que existan)
			@NamedStoredProcedureQuery(name = "Alumno.alumnoRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", 
					resultClasses = edu.unisys.academy.model.Alumno.class),
			@NamedStoredProcedureQuery(name = "Alumno.alumnosEdadMediaMinMax", procedureName = "calcula_max_min_media_edad",
					parameters = {
							@StoredProcedureParameter(mode = ParameterMode.INOUT, name = "edadmax", type = Integer.class),
							@StoredProcedureParameter(mode = ParameterMode.INOUT, name = "edadmin", type = Integer.class),
							@StoredProcedureParameter(mode = ParameterMode.INOUT, name = "edadMedia", type = Float.class),
					}),
			@NamedStoredProcedureQuery(name = "Alumno.alumnosNombreComo", procedureName = "obtenerAlumnosConNombreComo",
					parameters = {
							@StoredProcedureParameter(mode = ParameterMode.IN, name= "patron", type = String.class)
					}, resultClasses = edu.unisys.academy.model.Alumno.class)
		}
)
public class Alumno {
	
	//Validación más pontente:  @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}"
	
	@Id // Clave primaria: que sea un número
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Usa autoincremento SQL
	private Long id;
	
	//NO ES NECESARIO ANOTAR ESTOS ATRIBUTOS COMO COLUMNAS
	//LO VA A GENERERA AUTOMÁTICAMENTE
	//Validar el nombre para que tenga un tamaño entre 3-15 char
	
	@Size(min=3, max=15)
	private String nombre;
	
	@NotEmpty  //@NotNull y longitud mayor que 1
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(130)
	private int edad;
	
	@Lob //Large Object Binary
	@JsonIgnore //con esta anotación indico que este atributo no va en el JSON de respuesta: no se serialice
	private byte[] foto;
	
	//Flag para indicar si un alumno tiene foto o no:
	
	public Integer getFotohashCode() {
		Integer idev = null;
		
		if(this.foto != null) {
			idev = this.foto.hashCode();
		}
		
		return idev;
	}
	
	
	//Quiero saber cuándo se importa un registro
	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Column(name = "creado_en") //puedo especificar un nombre de columna para este atributo con COLUMN
	@Temporal(TemporalType.TIMESTAMP) //quiero que la fecha se guarde en FECHA HH:MM:SS:MSS
	private Date creadoEn;
	
	@PrePersist //El método anotado así se ejecuta automáticamente antes de guardar el objeto en la BD
	private void generarFecha() {
		//Obtengo la fecha actual
		this.creadoEn =  new Date();
	}
	
	
	public Date getCreadoEn() {
		return creadoEn;
	}
	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}


	public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}


	public Alumno() {
		super();
	}
	
	@JsonIgnoreProperties(value = {"alumnos"})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cursoId") //Define cuál es la clave ajena de la relación
	private Curso curso;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	// TODO: deberíamos hacer el equals para ayudar a diferenciar los alumnos
	
	
}
