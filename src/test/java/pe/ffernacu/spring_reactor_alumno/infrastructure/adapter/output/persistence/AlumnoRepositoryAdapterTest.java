package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveInsertOperation;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.persistence.repository.AlumnoRepository;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.entity.AlumnoEntity;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumno;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumnoEntity;

@ExtendWith(MockitoExtension.class)
class AlumnoRepositoryAdapterTest {

    @InjectMocks
    private AlumnoRepositoryAdapter alumnoRepositoryAdapter;
    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    @Mock
    private AlumnoMapper alumnoMapper;
    @Mock
    private ReactiveInsertOperation.ReactiveInsert<AlumnoEntity> reactiveInsert;


    @Test
    void save() {
        Alumno alumno = getAlumno();
        AlumnoEntity alumnoEntity = getAlumnoEntity();

        Mockito.when(r2dbcEntityTemplate.insert(AlumnoEntity.class)).thenReturn(reactiveInsert);
        Mockito.when(reactiveInsert.using(any())).thenReturn(Mono.just(alumnoEntity));
        Mockito.when(alumnoMapper.mapToEntity(any(Alumno.class))).thenReturn(alumnoEntity);
        Mockito.when(alumnoMapper.mapToModel(any(AlumnoEntity.class))).thenReturn(alumno);
        Mono<Alumno> result = alumnoRepositoryAdapter.save(alumno);

        StepVerifier.create(result)
                .assertNext(alumnoResult -> {
                    assertThat(alumnoResult.getEstado().name().equals("ACTIVO"));
                    assertThat(alumnoResult).isInstanceOf(Alumno.class);
                })
                .verifyComplete();
    }

    @Test
    void findById() {
        Alumno alumno = getAlumno();
        AlumnoEntity alumnoEntity = getAlumnoEntity();

        Mockito.when(alumnoRepository.findById(any(String.class))).thenReturn(Mono.just(alumnoEntity));
        Mockito.when(alumnoMapper.mapToModel(any(AlumnoEntity.class))).thenReturn(alumno);
        Mono<Alumno> result = alumnoRepositoryAdapter.findById("1");

        StepVerifier.create(result)
                .assertNext(alumnoResult -> {
                    assertThat(alumnoResult.getEstado().name().equals("ACTIVO"));
                    assertThat(alumnoResult).isInstanceOf(Alumno.class);
                })
                .verifyComplete();
    }

    @Test
    void findAllByStatus() {
        Alumno alumno = getAlumno();
        AlumnoEntity alumnoEntity = getAlumnoEntity();

        Mockito.when(alumnoRepository.findAllByEstado(any(String.class))).thenReturn(Flux.just(alumnoEntity));
        Mockito.when(alumnoMapper.mapToModel(any(AlumnoEntity.class))).thenReturn(alumno);
        Flux<Alumno> result = alumnoRepositoryAdapter.findAllByStatus("ACTIVO");

        StepVerifier.create(result)
                .assertNext(alumnoResult -> {
                    assertThat(alumnoResult.getEstado().name().equals("ACTIVO"));
                    assertThat(alumnoResult).isInstanceOf(Alumno.class);
                })
                .verifyComplete();
    }
}