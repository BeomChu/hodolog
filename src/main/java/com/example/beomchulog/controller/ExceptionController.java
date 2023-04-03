package com.example.beomchulog.controller;


import com.example.beomchulog.exception.BeomchulogException;
import com.example.beomchulog.exception.PostNotFound;
import com.example.beomchulog.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BeomchulogException.class)
    public ResponseEntity<ErrorResponse> postNotFound(PostNotFound e) {

        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        ResponseEntity<ErrorResponse> responseEntity = ResponseEntity.status(statusCode)
                .body(body);

        return responseEntity;

    }

}
