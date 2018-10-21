package org.poem.router;

import org.poem.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author poem
 */
@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> hello(HelloHandler helloHandler){
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        helloHandler::handle);
    }
}
