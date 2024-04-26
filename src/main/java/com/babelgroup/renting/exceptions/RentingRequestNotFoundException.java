package com.babelgroup.renting.exceptions;

public class RentingRequestNotFoundException extends Exception {
    public RentingRequestNotFoundException(){
        super("El ID de la solicitud de renting no existe.");
    }
}
