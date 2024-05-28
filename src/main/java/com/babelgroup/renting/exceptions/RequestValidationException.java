package com.babelgroup.renting.exceptions;

import org.springframework.http.HttpStatus;

public abstract class RequestValidationException extends Exception {

    protected HttpStatus httpStatus;
    protected String httpMessage;

    abstract HttpStatus setearHttpStatus();
    abstract String setearHttpMessage();


    public RequestValidationException(String exceptionMessage){
        super(exceptionMessage); //normal de todas las excepciones
        this.httpStatus = setearHttpStatus();
        this.httpMessage = setearHttpMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getHttpMessage() {
        return httpMessage;
    }
}
