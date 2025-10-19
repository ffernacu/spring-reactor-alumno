package pe.ffernacu.spring_reactor_alumno.domain.port.out;

import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepositoryPort {
    Mono<Alumno> save(Alumno alumno);
    Flux<Alumno> findAllByStatus(String status);
    Mono<Alumno> findById(String id);
}
