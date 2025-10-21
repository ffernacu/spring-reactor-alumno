package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_alumno.application.port.input.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import pe.ffernacu.spring_reactor_alumno.infrastructure.validator.RequestValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumno;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumnoDto;

@ExtendWith(MockitoExtension.class)
class AlumnoHandlerTest {

    @InjectMocks
    private  AlumnoHandler alumnoHandler;
    @Mock
    private AlumnoServicePort alumnoServicePort;
    @Mock
    private AlumnoMapper alumnoMapper;
    @Mock
    private RequestValidator requestValidator;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        AlumnoDTO alumnoDto = getAlumnoDto();
        Alumno alumno = getAlumno();

        MockServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(alumnoDto));

        Mockito.when(requestValidator.validate(alumnoDto)).thenReturn(Mono.just(alumnoDto));
        Mockito.when(alumnoMapper.mapToModel(any(AlumnoDTO.class))).thenReturn(alumno);
        Mockito.when(alumnoServicePort.create(any())).thenReturn(Mono.just(alumno));
        Mockito.when(alumnoMapper.mapToDto(any(Alumno.class))).thenReturn(alumnoDto);

        Mono<ServerResponse> result  = alumnoHandler.save(serverRequest);

        StepVerifier.create(result)
                .assertNext(serverResponse -> {
                    assertThat(serverResponse.statusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(serverResponse).isInstanceOf(ServerResponse.class);
                })
                .verifyComplete();

        verify(requestValidator,times(1)).validate(alumnoDto);
        verify(alumnoMapper, times(1)).mapToModel(any(AlumnoDTO.class));
        verify(alumnoMapper, times(1)).mapToDto(any(Alumno.class));
        verify(alumnoServicePort, times(1)).create(any(Alumno.class));

    }

    @Test
    void filter() {

        AlumnoDTO alumnoDto = getAlumnoDto();
        Alumno alumno = getAlumno();

        MockServerRequest serverRequest = MockServerRequest.builder()
                .pathVariable("status", "ACTIVO").build();

        Mockito.when(alumnoServicePort.filter(any())).thenReturn(Flux.just(alumno));
        Mockito.when(alumnoMapper.mapToDto(any(Alumno.class))).thenReturn(alumnoDto);
        Mono<ServerResponse> result  = alumnoHandler.filter(serverRequest);

        StepVerifier.create(result)
                .assertNext(serverResponse -> {
                    assertThat(serverResponse.statusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(serverResponse).isInstanceOf(ServerResponse.class);
                })
                .verifyComplete();

        verify(alumnoMapper, times(1)).mapToDto(any(Alumno.class));
        verify(alumnoServicePort, times(1)).filter(any(String.class));
    }

}