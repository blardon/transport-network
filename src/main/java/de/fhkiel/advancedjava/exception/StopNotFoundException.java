package de.fhkiel.advancedjava.exception;

import de.fhkiel.advancedjava.model.StopType;

public class StopNotFoundException extends RuntimeException{
    public StopNotFoundException(Long id){
        super(String.format("Stop with ID %d not found.", id));
    }

    public StopNotFoundException(Long stationId, StopType type){
        super(String.format("Stop with type %s and ID %d not found.", type.name(), stationId));
    }
}