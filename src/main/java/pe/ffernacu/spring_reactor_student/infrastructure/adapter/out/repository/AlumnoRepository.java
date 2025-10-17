package pe.ffernacu.spring_reactor_student.infrastructure.adapter.out.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.ffernacu.spring_reactor_student.infrastructure.adapter.out.repository.entity.AlumnoEntity;

@Repository
public interface AlumnoRepository extends ReactiveCrudRepository<AlumnoEntity, String> {
}
