package org.dev.productservice.domain.exceptionhandler;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.dev.productservice.domain.Base.ResponseDTO;
import org.dev.productservice.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleProductNotFoundException(ProductNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO<>(
                        null,
                        ex.getMessage(),
                        true,
                        List.of()
                ));
    }

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void handleRequestNotPermitted() {
        // Do nothing because we just want to return the status code
    }
}
