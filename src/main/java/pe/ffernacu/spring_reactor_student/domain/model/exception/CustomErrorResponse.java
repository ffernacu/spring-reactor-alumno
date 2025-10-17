package pe.ffernacu.spring_reactor_student.domain.model.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse(LocalDateTime dateTime, String message)
{
}