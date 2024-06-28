package inviteme.restfull.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.model.response.WebResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WebResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        WebResponse<String> response = WebResponse.<String>builder()
                                                  .code("400")
                                                  .message("Validation failed")
                                                  .messageError(ex.getMessage())
                                                  .result("")
                                                  .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<WebResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        WebResponse<String> response = WebResponse.<String>builder()
                                                  .code("403")
                                                  .message("Access denied")
                                                  .messageError(ex.getMessage())
                                                  .result("")
                                                  .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<String>builder().messageError(exception.getReason()).code(exception.getStatusCode().toString()).build());
    }

}
