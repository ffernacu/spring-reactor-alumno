package pe.ffernacu.spring_reactor_student.infrastructure.mapper;

import org.mapstruct.Mapper;
import pe.ffernacu.spring_reactor_student.infrastructure.adapter.in.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_student.domain.model.Alumno;
import pe.ffernacu.spring_reactor_student.infrastructure.adapter.out.repository.entity.AlumnoEntity;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {
    AlumnoDTO mapToDto(Alumno domain);
    Alumno mapToModel(AlumnoDTO alumnoDTO);
    Alumno mapToModel(AlumnoEntity entity);
    AlumnoEntity mapToEntity(Alumno domain);
}
