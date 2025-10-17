package pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import pe.ffernacu.spring_reactor_student.infrastructure.mapper.AlumnoMapper;
import pe.ffernacu.spring_reactor_student.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_student.infrastructure.validator.RequestValidator;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicePort alumnoServicePort;
    private final AlumnoMapper alumnoMapper;
    private final RequestValidator requestValidator;

    @PostMapping
    public Mono<ResponseEntity<AlumnoDTO>> save(@RequestBody AlumnoDTO alumnoDTO,
                                                @RequestParam String id,
                                                ServerHttpRequest req){
        Mono<AlumnoDTO> alumnoDTOMono = requestValidator.validate(alumnoDTO);
        return alumnoDTOMono
                .flatMap(alumno -> alumnoServicePort.create(alumnoMapper.mapToModel(alumno), id))
                .map(alumnoMapper::mapToDto)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI() + "/" + e.getId()))
                        .body(e));
    }

    @GetMapping
    public Mono<ResponseEntity<List<AlumnoDTO>>> findAllByStatus(@RequestParam String status){
        return alumnoServicePort.findAllByStatus(status)
                .collectList()
                .map(e -> e.stream()
                        .map(alumnoMapper::mapToDto).toList())
                .map(e -> ResponseEntity.ok().body(e));
    }
}
