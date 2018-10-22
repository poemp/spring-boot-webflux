package org.poem.handler.file;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author poem
 * 文件上传和下载
 */
@Component
public class FileUploadHandler implements HandlerFunction<ServerResponse> {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadHandler.class);
    /**
     * 上传文件
     * @param serverRequest
     * @return
     */
    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return  serverRequest.multipartData().flatMap(stringPartMultiValueMap -> {
            stringPartMultiValueMap.forEach((key,value)->{
                value.forEach(part ->{
                    FilePart filePart = (FilePart) part;
                    String fileName = filePart.filename();
                    logger.info(String.format("%20s %20s",key, fileName));
                });
            });
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(stringPartMultiValueMap.size()));
        });
    }


    /**
     * 下载文件
     * @param request 请求
     * @return
     */
    public Mono<ServerResponse> downLoadFile(ServerRequest request){
        File file = new File("pom.xml");
        return ServerResponse
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+file.getName())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromDataBuffers(Mono.create(r->{
                    try {
                        DataBuffer  dataBuffer = new DefaultDataBufferFactory()
                                .wrap(IOUtils.toByteArray(new FileInputStream(file)));
                        r.success(dataBuffer);
                    } catch (IOException e) {
                        logger.error(e.getMessage(),e);
                        e.printStackTrace();
                        r.error(e);
                    }

                })));

    }
}
