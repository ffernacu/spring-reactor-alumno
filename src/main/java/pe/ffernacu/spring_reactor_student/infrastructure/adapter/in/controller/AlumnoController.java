package pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.ffernacu.spring_reactor_student.infrastructure.mapper.AlumnoMapper;
import pe.ffernacu.spring_reactor_student.domain.model.Alumno;
import pe.ffernacu.spring_reactor_student.domain.port.in.AlumnoServicePort;
import pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.dto.AlumnoDTO;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoServicePort alumnoServicePort;
    private final AlumnoMapper alumnoMapper;

    @PostMapping
    public Mono<ResponseEntity<AlumnoDTO>> save(@RequestBody AlumnoDTO alumnoDTO, @RequestParam String id){
        Alumno alumno = alumnoMapper.mapToModel(alumnoDTO);
        return alumnoServicePort.create(alumno, id)
                .map(alumnoMapper::mapToDto)
                .map(e -> ResponseEntity
                        .created(URI.create("/save"))
                        .body(e));
    }

    @GetMapping
    public Mono<ResponseEntity<List<AlumnoDTO>>> findAll(){
        return alumnoServicePort.list()
                .collectList()
                .map(e -> e.stream()
                        .map(alumnoMapper::mapToDto).toList())
                .map(e -> ResponseEntity.ok().body(e));
    }
}
