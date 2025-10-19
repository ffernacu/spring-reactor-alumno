package pe.ffernacu.spring_reactor_alumno.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.ffernacu.spring_reactor_alumno.application.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.infrastructure.entity.AlumnoEntity;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    AlumnoDTO mapToDto(Alumno alumno);

    @Mapping(target = "estado", expression = "java(Estado.validarEstado(alumnoDTO.getEstado()))")
    Alumno mapToModel(AlumnoDTO alumnoDTO);

    Alumno mapToModel(AlumnoEntity alumnoEntity);

    AlumnoEntity mapToEntity(Alumno alumno);

}
