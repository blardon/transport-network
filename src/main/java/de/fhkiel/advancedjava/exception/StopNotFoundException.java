package de.fhkiel.advancedjava.exception;

import de.fhkiel.advancedjava.model.schedule.StopType;

/**
 * StopNotFoundException is thrown when a stop could not be found.
 *
 * @author Bennet v. Lardon
 */
public class StopNotFoundException extends RuntimeException {
    public StopNotFoundException(Long id) {
        super(String.format("Stop with ID %d not found.", id));
    }

    public StopNotFoundException(Long stationId, StopType type) {
        super(String.format("Stop with type %s and ID %d not found.", type.toString(), stationId));
    }

    public StopNotFoundException(String stationName, StopType type) {
        super(String.format("Stop with type %s and name %s not found.", type.toString(), stationName));
    }
}
