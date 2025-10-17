package pe.ffernacu.spring_reactor_student.application.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_student.domain.model.Alumno;
import pe.ffernacu.spring_reactor_student.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_student.domain.port.out.AlumnoRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AlumnoServiceAdapter implements AlumnoServicePort {

    private final AlumnoRepositoryPort alumnoRepositoryPort;

    @Override
    public Mono<Alumno> create(Alumno alumno, String id) {
        return alumnoRepositoryPort.save(alumno, id);
    }

    @Override
    public Flux<Alumno> list() {
        return alumnoRepositoryPort.findAll();
    }
}
