package pe.ffernacu.spring_reactor_alumno.domain.model;

import lombok.Data;

@Data
public class Alumno {
    private String id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;
}
