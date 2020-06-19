package de.fhkiel.advancedjava.exception;

import de.fhkiel.advancedjava.model.schedule.StopType;

/**
 * LegNotFoundException is thrown when a leg could not be found.
 *
 * @author Bennet v. Lardon
 */
public class LegNotFoundException extends RuntimeException {
    public LegNotFoundException(StopType type, String fromStation, String toStation) {
        super(String.format("Connection with type %s between %s and %s not found.", type.toString(), fromStation, toStation));
    }

    public LegNotFoundException(Long id) {
        super(String.format("Connection with ID %d not found.", id));
    }
}
