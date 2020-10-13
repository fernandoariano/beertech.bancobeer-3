package br.com.beertechtalents.lupulo.pocmq.exception;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public @ResponseBody
    ErrorMessage handleExceptionNotFound(Exception exception, ServletWebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .path(request.getRequest().getRequestURL().toString())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .description(exception.getCause().getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public @ResponseBody
    ErrorMessage handleBadRequestException(Exception exception, ServletWebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .path(request.getRequest().getRequestURL().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .description(exception.getCause().getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ErrorMessage handleInternalServerErrorException(Exception exception, ServletWebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .path(request.getRequest().getRequestURL().toString())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .description(exception.getCause().getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return errorMessage;
    }


}
