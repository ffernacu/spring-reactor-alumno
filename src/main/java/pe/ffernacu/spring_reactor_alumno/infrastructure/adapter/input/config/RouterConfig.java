package pe.ffernacu.spring_reactor_alumno.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.ffernacu.spring_reactor_alumno.infrastructure.adapter.input.handler.AlumnoApiHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.routes.alumno")
public class RouterConfig {

    private String savePath;
    private String filterByStatusPath;

    @Bean
    public RouterFunction<ServerResponse> routesAlumno(AlumnoApiHandler alumnoHandler){
        return route(POST(savePath), alumnoHandler::save)
                .andRoute(GET(filterByStatusPath), alumnoHandler::filterByStatus);
    }
}
