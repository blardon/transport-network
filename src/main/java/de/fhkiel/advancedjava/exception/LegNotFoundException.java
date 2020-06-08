package de.fhkiel.advancedjava.exception;

import de.fhkiel.advancedjava.model.StopType;

public class LegNotFoundException extends RuntimeException{
    public LegNotFoundException(StopType type, String fromStation, String toStation){
        super(String.format("Connection with type %s between %s and %s not found.", type.name(), fromStation, toStation));
    }
}
