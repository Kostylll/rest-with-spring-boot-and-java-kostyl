package br.com.Kostylll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStoragreException extends RuntimeException {

    public FileStoragreException(String message) {
        super(message);
    }

    public FileStoragreException(String message , Throwable cause) {
        super(message, cause);
    }
}
