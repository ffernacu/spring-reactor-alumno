package pe.ffernacu.spring_reactor_alumno.domain.model;

import java.util.Arrays;

public enum Estado {
    ACTIVO,
    INACTIVO;

    public static Estado validarEstado(String estado){
        return Arrays.stream(Estado.values())
                .filter(e -> e.name().equals(estado))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El estado " + estado + " es desconocido"));
    }

}
