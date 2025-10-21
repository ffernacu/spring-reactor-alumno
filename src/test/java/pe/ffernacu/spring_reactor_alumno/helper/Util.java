package pe.ffernacu.spring_reactor_alumno.helper;

import pe.ffernacu.spring_reactor_alumno.domain.model.Alumno;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.dto.AlumnoDTO;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.output.entity.AlumnoEntity;

public class Util {

    public static AlumnoDTO getAlumnoDto(){
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId("1");
        alumnoDTO.setNombre("Andres");
        alumnoDTO.setApellido("Lopez");
        alumnoDTO.setEstado("ACTIVO");
        alumnoDTO.setEdad(30);
        return alumnoDTO;
    }

    public static Alumno getAlumno(){
        Alumno alumno = new Alumno();
        alumno.setId("1");
        alumno.setNombre("Andres");
        alumno.setApellido("Lopez");
        alumno.setEstado(Alumno.Estado.ACTIVO);
        alumno.setEdad(30);
        return alumno;
    }

    public static AlumnoEntity getAlumnoEntity(){
        AlumnoEntity alumnoEntity = new AlumnoEntity();
        alumnoEntity.setId("1");
        alumnoEntity.setNombre("Andres");
        alumnoEntity.setApellido("Lopez");
        alumnoEntity.setEstado("ACTIVO");
        alumnoEntity.setEdad(30);
        return alumnoEntity;
    }
}
