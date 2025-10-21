package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.entity.AlumnoEntity;
import reactor.core.publisher.Flux;

@Repository
public interface AlumnoRepository extends ReactiveCrudRepository<AlumnoEntity, String> {
    Flux<AlumnoEntity> findAllByEstado(String estado);
}
