package edu.unisys.academy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.unisys.academy.model.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table (name = "cursos")
public class Curso {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    /*
     * Fetch - ¿Cuándo se hace la select de los datos relacionados? ¿De los alumnos del curso?
     * 		Cada vez que leo un curso, se cargan todos los alumnos (de la otra tabla) - EAGER
     * 		Cuando leo un curso, no se cargan los alumnos hasta que no se accede a la lista - LAZY
     * */
    
    @JsonIgnoreProperties (value = {"curso"}) //Cuando leas los alumnos de un curso, de esos alumnos ignora el atributo curso y así evitamos el bucle
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso") //mappedBy: qué atributo de la entidad Alumno apunta a curso
    private List<Alumno> alumnos;
    
    @Column(name = "creado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;
    
    @PrePersist
    public void prePersist ()
    {
        this.creadoEn = new Date();
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

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }

    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", creadoEn=" + creadoEn + "]";
    }

    public Curso(Long id, String nombre, Date creadoEn) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.creadoEn = creadoEn;
    }
    
    public Curso() {
        // TODO Auto-generated constructor stub
    }
}