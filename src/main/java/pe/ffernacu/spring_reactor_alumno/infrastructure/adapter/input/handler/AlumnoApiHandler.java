package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.domain.model.Estado;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import pe.ffernacu.spring_reactor_alumno.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.infrastructure.validator.RequestValidator;
import reactor.core.publisher.Mono;

import java.net.URI;

import static pe.ffernacu.spring_reactor_alumno.domain.model.Estado.validarEstado;

@Component
@RequiredArgsConstructor
public class AlumnoHandler {

    private final AlumnoServicePort alumnoServicePort;
    private final AlumnoMapper alumnoMapper;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<AlumnoDTO> alumnoDTOMono = serverRequest.bodyToMono(AlumnoDTO.class);
        return alumnoDTOMono
                .flatMap(requestValidator::validate)
                .flatMap(alumno -> alumnoServicePort.create(convertToModel(alumno)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(serverRequest.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    public Mono<ServerResponse> filterByStatus(ServerRequest serverRequest){
        String status = serverRequest.pathVariable("status");
        Estado statusValidated = validarEstado(status);
        return alumnoServicePort.findAllByStatus(statusValidated.name())
                .collectList()
                .map(e -> e.stream()
                        .map(this::convertToDto)
                        .toList())
                .flatMap(e -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromValue(e)));
    }

    private Alumno convertToModel(AlumnoDTO alumnoDTO){
        return alumnoMapper.mapToModel(alumnoDTO);
    }

    private AlumnoDTO convertToDto(Alumno alumno){
        return alumnoMapper.mapToDto(alumno);
    }
}
