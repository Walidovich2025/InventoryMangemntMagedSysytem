package org.inventory.magedsystem.exceptions;

import org.inventory.magedsystem.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response>handleAllExceptions(Exception ex) {
        Response response = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new  ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response>handleNotFoundExceptions(NotFoundException ex) {
        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new  ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NameValueRequiredException.class)
    public ResponseEntity<Response>handlNameValueRequiredException(NameValueRequiredException ex) {
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new  ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCarednitalsException.class)
    public ResponseEntity<Response>handlInvalidCarednitalsException(InvalidCarednitalsException ex) {
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();
        return new  ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
