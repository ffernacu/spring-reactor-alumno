package pe.ffernacu.spring_reactor_alumno.application.port.input;

import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoServicePort {
    Mono<Alumno> create(Alumno alumno);
    Flux<Alumno> filter(String status);
}
