package pe.ffernacu.spring_reactor_alumno.infrastructure.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AlumnoDTO {

    @NotBlank
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
