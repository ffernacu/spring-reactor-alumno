package pe.ffernacu.spring_reactor_alumno.domain.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse(LocalDateTime dateTime, String message)
{
}