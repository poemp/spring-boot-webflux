package org.poem.handler.user;

import org.poem.handler.user.vo.UserVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author poem
 */
@Component
public class UserHandler {

    /**
     * 获取用户的信息
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        UserVO userVO = new UserVO();
        userVO.setName("user");
        userVO.setAge(20);
        userVO.setLock(false);
        userVO.setId(UUID.randomUUID().getLeastSignificantBits());
        return serverRequest.bodyToMono(UserHandler.class)
                .flatMap(banner -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(userVO)));
    }

    /**
     * 跟新用户信息
     * @param request
     * @return
     */
    public Mono<ServerResponse> updateUser(ServerRequest request){
        String userId = request.pathVariable("id");
        UserVO userVO = new UserVO();
        userVO.setName("user");
        userVO.setAge(20);
        userVO.setLock(false);
        userVO.setId(Long.valueOf(userId));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(userVO));
    }
}
