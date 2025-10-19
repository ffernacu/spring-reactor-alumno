package pe.ffernacu.spring_reactor_alumno.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ffernacu.spring_reactor_alumno.application.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.domain.model.Estado;
import pe.ffernacu.spring_reactor_alumno.domain.model.exception.AlumnoFoundException;
import pe.ffernacu.spring_reactor_alumno.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_alumno.domain.port.out.AlumnoRepositoryPort;
import pe.ffernacu.spring_reactor_alumno.application.validator.RequestValidator;
import pe.ffernacu.spring_reactor_alumno.infrastructure.mapper.AlumnoMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pe.ffernacu.spring_reactor_alumno.domain.model.Estado.validarEstado;

@Service
@RequiredArgsConstructor
public class AlumnoUseCaseHandler implements AlumnoServicePort {

    private final AlumnoRepositoryPort alumnoRepositoryPort;
    private final AlumnoMapper alumnoMapper;
    private final RequestValidator requestValidator;

    @Override
    public Mono<AlumnoDTO> create(AlumnoDTO alumnoDTO) {
        Mono<AlumnoDTO> alumnoDTOMono = requestValidator.validate(alumnoDTO);
        return alumnoDTOMono
                .flatMap(e -> alumnoRepositoryPort.findById(convertToModel(e).getId()))
                .hasElement()
                .flatMap( e -> {
                    if(e) {
                        return Mono.error(new AlumnoFoundException("No se pudo hacer la grabación debido al id " + alumnoDTO.getId() + " es repetido" ));
                    }
                    else {
                        return alumnoRepositoryPort.save(convertToModel(alumnoDTO))
                                .map(this::convertToDto);
                    }
                });
    }

    @Override
    public Flux<AlumnoDTO> filter(String status) {
        Estado statusValidated = validarEstado(status);
        return alumnoRepositoryPort.findAllByStatus(statusValidated.name())
                        .map(this::convertToDto);
    }

    private Alumno convertToModel(AlumnoDTO alumnoDTO){
        return alumnoMapper.mapToModel(alumnoDTO);
    }

    private AlumnoDTO convertToDto(Alumno alumno){
        return alumnoMapper.mapToDto(alumno);
    }
}
