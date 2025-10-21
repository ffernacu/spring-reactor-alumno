package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_alumno.application.port.input.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.infrastructure.validator.RequestValidator;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import reactor.core.publisher.Mono;

import java.net.URI;

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
                .map(this::convertToModel)
                .flatMap(alumnoServicePort::create)
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(serverRequest.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    public Mono<ServerResponse> filter(ServerRequest serverRequest){
        String status = serverRequest.pathVariable("status");
        return alumnoServicePort.filter(status)
                .map(this::convertToDto)
                .collectList()
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
