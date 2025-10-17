package pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.dto;

import lombok.Data;

@Data
public class AlumnoDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String estado;
    private Integer edad;
}
