package ru.otus.spring.controllers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import ru.otus.spring.exceptions.EntityCanNotBeDeletedException;
import ru.otus.spring.exceptions.EntityNotFoundException;

import java.util.Map;

@Component
public class ErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ErrorWebExceptionHandler(DefaultErrorAttributes defaultErrorAttributes,
                                    ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(defaultErrorAttributes, new Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private @NotNull Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        if (error instanceof EntityCanNotBeDeletedException) {
            errorPropertiesMap.put("status", HttpStatus.BAD_REQUEST.value());
            errorPropertiesMap.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        if (error instanceof EntityNotFoundException) {
            errorPropertiesMap.put("status", HttpStatus.NOT_FOUND.value());
            errorPropertiesMap.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        errorPropertiesMap.put("message", error.getMessage());
        int status = (int) errorPropertiesMap.get("status");
        return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));
    }
}
