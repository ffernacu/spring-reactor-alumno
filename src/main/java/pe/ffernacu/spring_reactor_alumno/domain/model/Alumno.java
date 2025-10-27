package pe.ffernacu.spring_reactor_alumno.domain.model;

import lombok.Data;
import pe.ffernacu.spring_reactor_alumno.domain.exception.AlumnoBadRequestException;

import java.util.Arrays;
import java.util.regex.Pattern;

@Data
public class Alumno {
    private String id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;

    public enum Estado {
        ACTIVO,
        INACTIVO
    }

    private static Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");

    public void validarNombre() {
        if (!NAME_PATTERN.matcher(nombre).matches()) {
            throw new AlumnoBadRequestException("El valor " + nombre + " contiene caracteres especiales no permitidos.");
        }
    }

    public static Estado validarEstado(String status){
        return Arrays.stream(Estado.values())
                .filter(e -> e.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new AlumnoBadRequestException("El estado " + status + " es desconocido"));
    }
}
