package de.fhkiel.advancedjava.exception;

/**
 * VehicleNotFoundException is thrown when a vehicle could not be found.
 *
 * @author Bennet v. Lardon
 */
public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super(String.format("No vehicle found with id %d", id));
    }
}
