package pe.ffernacu.spring_reactor_alumno.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.ffernacu.spring_reactor_alumno.domain.exception.AlumnoBadRequestException;

import static org.junit.jupiter.api.Assertions.*;
import static pe.ffernacu.spring_reactor_alumno.domain.model.Alumno.validarEstado;
import static pe.ffernacu.spring_reactor_alumno.helper.Util.getAlumno;

@ExtendWith(MockitoExtension.class)
class AlumnoTest {

    @Mock
    private Alumno alumno;

    @BeforeEach
    void setUp() {
        alumno = getAlumno();
        alumno.setNombre("Juan#Pérez");
    }

    @Test
    void validarNombre_error() {

        AlumnoBadRequestException exception = assertThrows(AlumnoBadRequestException.class, () -> {
            alumno.validarNombre();
        });

        String expectedMessage = "El valor " + alumno.getNombre() + " contiene caracteres especiales no permitidos.";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    void validarEstado_error() {

        String status = "ACTIVE";

        AlumnoBadRequestException exception = assertThrows(AlumnoBadRequestException.class, () -> {
            validarEstado(status);
        });

        String expectedMessage = "El estado " + status + " es desconocido";
        assertEquals(expectedMessage, exception.getMessage());
    }
}