package com.babelgroup.renting.exceptions;

import org.springframework.http.HttpStatus;

public class ClientsAlreadyExistsException extends RequestValidationException {

    public ClientsAlreadyExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }

    @Override
    HttpStatus setearHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String setearHttpMessage() {
        return "El cliente ya existe";
    }

}
