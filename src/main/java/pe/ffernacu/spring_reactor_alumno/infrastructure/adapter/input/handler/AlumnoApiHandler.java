package pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_alumno.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.application.dto.AlumnoDTO;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AlumnoApiHandler {

    private final AlumnoServicePort alumnoServicePort;

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<AlumnoDTO> alumnoDTOMono = serverRequest.bodyToMono(AlumnoDTO.class);
        return alumnoDTOMono
                .flatMap(alumnoServicePort::create)
                .flatMap(e -> ServerResponse
                        .created(URI.create(serverRequest.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    public Mono<ServerResponse> filter(ServerRequest serverRequest){
        String status = serverRequest.pathVariable("status");
        return alumnoServicePort.filter(status)
                .collectList()
                .flatMap(e -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromValue(e)));
    }

}
