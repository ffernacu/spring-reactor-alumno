package pe.ffernacu.spring_reactor_student.domain.port.out;

import pe.ffernacu.spring_reactor_student.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepositoryPort {
    Mono<Alumno> save(Alumno alumno, String id);
    Flux<Alumno> findAll();
    Mono<Alumno> findById(String id);
}
