package com.babelgroup.renting.exceptions;

import org.springframework.http.HttpStatus;

public class CountryOrProvinceException extends RequestValidationException {

    public CountryOrProvinceException(String exceptionMessage) {
        super(exceptionMessage);
    }

    @Override
    HttpStatus setearHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    String setearHttpMessage() {
        return "Error en el pais o provincia";
    }

}