package pe.ffernacu.spring_reactor_alumno.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlumnoFoundException extends RuntimeException {

    public AlumnoFoundException(String message) {
        super(message);
    }
}
