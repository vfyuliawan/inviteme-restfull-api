package inviteme.restfull.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import inviteme.restfull.model.response.WebResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
       public ResponseEntity<WebResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = ex.getMostSpecificCause().getMessage();
        
        WebResponse<String> response = WebResponse.<String>builder()
            .code("400")
            .messageError("Bad Request: " + errorMessage)
            .build();
        
        return new ResponseEntity<WebResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGenericException(Exception ex) {
        WebResponse<String> response = WebResponse.<String>builder()
            .code("500")
            .messageError("An unexpected error occurred: " + ex.getMessage())
            .build();
        
        return new ResponseEntity<WebResponse<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
