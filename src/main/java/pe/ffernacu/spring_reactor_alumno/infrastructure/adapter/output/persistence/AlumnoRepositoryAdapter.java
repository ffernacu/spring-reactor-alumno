package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.domain.port.out.AlumnoRepositoryPort;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.api.AlumnoRepository;
import pe.ffernacu.spring_reactor_alumno.infrastructure.entity.AlumnoEntity;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AlumnoRepositoryAdapter implements AlumnoRepositoryPort {

    private final AlumnoRepository alumnoRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final AlumnoMapper alumnoMapper;

    @Override
    public Mono<Alumno> save(Alumno alumno) {
        var alumnoEntity = alumnoMapper.mapToEntity(alumno);
        return r2dbcEntityTemplate.insert(AlumnoEntity.class)
                                .using(alumnoEntity)
                                .map(alumnoMapper::mapToModel);
    }

    @Override
    public Mono<Alumno> findById(String id) {
        return alumnoRepository.findById(id)
                .map(alumnoMapper::mapToModel);
    }

    @Override
    public Flux<Alumno> findAllByStatus(String status) {
        return alumnoRepository.findAllByEstado(status)
                .map(alumnoMapper::mapToModel);
    }

}
