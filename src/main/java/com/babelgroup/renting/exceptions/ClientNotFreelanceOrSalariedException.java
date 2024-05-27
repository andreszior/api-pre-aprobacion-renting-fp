package com.babelgroup.renting.exceptions;

import org.springframework.http.HttpStatus;

public class ClientNotFreelanceOrSalariedException extends RequestValidationException {

    public ClientNotFreelanceOrSalariedException(String exceptionMessage) {
        super(exceptionMessage);
    }

    @Override
    HttpStatus setearHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    String setearHttpMessage() {
        return "El cliente no está registrado ni como libre ni asalariado";
    }
}
