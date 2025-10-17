package pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AlumnoDTO {

    private String id;

    @Size(min = 1, max = 16)
    @NotBlank
    private String nombre;

    @Size(min = 1, max = 16)
    @NotBlank
    private String apellido;

    @NotBlank
    private String estado;

    @NotNull
    @Min(0)
    @Max(120)
    private Integer edad;
}
