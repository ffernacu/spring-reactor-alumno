package pe.ffernacu.spring_reactor_alumno.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("alumnos")
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoEntity {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String estado;
    private Integer edad;
}
