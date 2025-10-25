package pe.ffernacu.spring_reactor_alumno.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.ffernacu.spring_reactor_alumno.application.port.output.AlumnoRepositoryPort;
import pe.ffernacu.spring_reactor_alumno.domain.exception.AlumnoBadRequestException;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumno;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @InjectMocks
    private AlumnoService alumnoService;
    @Mock
    private AlumnoRepositoryPort alumnoRepositoryPort;

    @Test
    void create() {
        Alumno alumno = getAlumno();
        Mockito.when(alumnoRepositoryPort.findById(any())).thenReturn(Mono.empty());
        Mockito.when(alumnoRepositoryPort.save(any())).thenReturn(Mono.just(alumno));
        Mono<Alumno> result = alumnoService.create(alumno);

        StepVerifier.create(result)
                .assertNext(alumnoResult -> {
                    assertThat(alumnoResult.getEstado().name().equals("ACTIVO"));
                    assertThat(alumnoResult).isInstanceOf(Alumno.class);
                })
                .verifyComplete();
    }

    @Test
    void create_error() {
        Alumno alumno = getAlumno();
        Mockito.when(alumnoRepositoryPort.findById(any())).thenReturn(Mono.just(alumno));
        Mono<Alumno> result = alumnoService.create(alumno);

        StepVerifier.create(result)
                .expectError(AlumnoBadRequestException.class)
                .verify();
    }


    @Test
    void filter() {
        Alumno alumno = getAlumno();
        Mockito.when(alumnoRepositoryPort.findAllByStatus(any())).thenReturn(Flux.just(alumno));
        Flux<Alumno> result = alumnoService.filter("ACTIVO");

        StepVerifier.create(result)
                .assertNext(alumnoResult -> {
                    assertThat(alumnoResult.getEstado().name().equals("ACTIVO"));
                    assertThat(alumnoResult).isInstanceOf(Alumno.class);
                })
                .verifyComplete();
    }
}