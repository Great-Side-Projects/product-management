package org.dev.productservice.domain.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductInternalServerErrorException extends RuntimeException {
    public ProductInternalServerErrorException(String message) {
        super(message);
    }
}