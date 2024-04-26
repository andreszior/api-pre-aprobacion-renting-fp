package com.babelgroup.renting.exceptions;

public class EmptyRentingRequestException extends Exception {
    public EmptyRentingRequestException(){
        super("No es posible crear una solicitud de renting con un objeto nulo.");
    }
}
