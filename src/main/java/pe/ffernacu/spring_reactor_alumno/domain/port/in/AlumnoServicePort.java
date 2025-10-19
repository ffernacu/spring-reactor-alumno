package pe.ffernacu.spring_reactor_student.domain.port.in;

import pe.ffernacu.spring_reactor_student.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoServicePort {
    Mono<Alumno> create(Alumno alumno);
    Flux<Alumno> findAllByStatus(String field);
}
