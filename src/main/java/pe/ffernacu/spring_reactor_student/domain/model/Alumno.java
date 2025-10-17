package pe.ffernacu.spring_reactor_student.domain.model;

import lombok.Data;

@Data
public class Alumno {
    private String id;
    private String nombre;
    private String apellido;
    private String estado;
    private Integer edad;
}
