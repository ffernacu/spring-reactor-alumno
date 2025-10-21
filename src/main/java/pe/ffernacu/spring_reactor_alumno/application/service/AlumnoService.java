package pe.ffernacu.spring_reactor_alumno.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.domain.exception.AlumnoFoundException;
import pe.ffernacu.spring_reactor_alumno.application.port.input.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.application.port.output.AlumnoRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AlumnoService implements AlumnoServicePort {

    private final AlumnoRepositoryPort alumnoRepositoryPort;

    @Override
    public Mono<Alumno> create(Alumno alumno) {
        alumno.validarNombre();
        return alumnoRepositoryPort.findById(alumno.getId())
                .hasElement()
                .flatMap( e -> {
                    if(e) {
                        return Mono.error(new AlumnoFoundException("No se pudo hacer la grabación debido al id " + alumno.getId() + " es repetido" ));
                    }
                    else {
                        return alumnoRepositoryPort.save(alumno);
                    }
                });
    }

    @Override
    public Flux<Alumno> filter(String status) {
        Alumno.Estado statusValidated = Alumno.validarEstado(status);
        return alumnoRepositoryPort.findAllByStatus(statusValidated.name());
    }

}
