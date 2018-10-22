package org.poem.router;

import org.poem.handler.file.FileUploadHandler;
import org.poem.handler.HelloHandler;
import org.poem.handler.user.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HelloHandler helloHandler;

    @Autowired
    private FileUploadHandler fileUploadHandler;

    @Autowired
    private UserHandler userHandler;


    /**
     * 路由处理
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> hello() {
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        helloHandler)
                .andRoute(RequestPredicates.GET("/user/getUser"),
                        userHandler::getUser)
                .andRoute(RequestPredicates.POST("/user/getUser/{id}"),
                        userHandler::updateUser)
                .andRoute(RequestPredicates.POST("/file/uploadFile"),
                        fileUploadHandler)
                .andRoute(RequestPredicates.GET("/file/download"),
                        fileUploadHandler::downLoadFile);
    }
}
