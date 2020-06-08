package de.fhkiel.advancedjava.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(Long id){
        super(String.format("No vehicle found with id %d", id));
    }
}
