package br.com.beertechtalents.lupulo.pocmq.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessage {
    private int statusCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime timestamp;

    private String message;

    private String path;

    private String description;
}
