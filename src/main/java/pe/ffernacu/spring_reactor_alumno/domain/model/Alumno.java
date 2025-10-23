package pe.ffernacu.spring_reactor_alumno.domain.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    public void validarNombre() {
        Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
        if (!NAME_PATTERN.matcher(nombre).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor " + nombre + " contiene caracteres especiales no permitidos.");
        }
    }

    public static Estado validarEstado(String status){
        return Arrays.stream(Estado.values())
                .filter(e -> e.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado " + status + " es desconocido"));
    }
}
