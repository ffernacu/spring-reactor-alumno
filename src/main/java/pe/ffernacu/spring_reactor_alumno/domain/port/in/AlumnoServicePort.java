package pe.ffernacu.spring_reactor_alumno.domain.port.in;

import pe.ffernacu.spring_reactor_alumno.application.dto.AlumnoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoServicePort {
    Mono<AlumnoDTO> create(AlumnoDTO alumnoDTO);
    Flux<AlumnoDTO> filter(String status);
}
