package pe.ffernacu.spring_reactor_student.infrastructure.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T t){
        if(t == null){
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request"));
        }

        Set<ConstraintViolation<T>> contraints = validator.validate(t);

        if(contraints == null || contraints.isEmpty()){
            return Mono.just(t);
        }

        Map<String, String> errors =  contraints.stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existingValue, newValue) -> existingValue + ", " + newValue
                ));

        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString()));
    }

}