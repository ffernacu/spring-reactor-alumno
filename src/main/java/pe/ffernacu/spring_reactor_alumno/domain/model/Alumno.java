package pe.ffernacu.spring_reactor_alumno.domain.model;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class Alumno {
    private String id;
    private String nombre;
    private String apellido;
    private Estado estado;
    private Integer edad;

    private Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");

    public void validarNombre(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("El valor " + name + " contiene caracteres especiales no permitidos.");
        }
    }

}
