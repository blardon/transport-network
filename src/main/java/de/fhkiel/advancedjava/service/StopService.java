package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StopNotFoundException;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Manages stops.
 *
 * @author Bennet v. Lardon
 */
@Service
public class StopService {

    private StopRepository stopRepository;

    @Autowired
    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public void deleteAllStops() {
        this.stopRepository.deleteAll();
    }

    public void saveAllStops(Collection<Stop> stops) {
        this.stopRepository.saveAll(stops);
    }

    public Collection<Stop> findStopsByType(StopType type) {
        final ArrayList<Stop> result = new ArrayList<>();
        this.stopRepository.findStopsByType(type).forEach(result::add);
        return result;
    }

    /**
     * <p>Finds a specific stop type at a station with a given name
     * </p>
     *
     * @param stationName the name of the Station
     * @param type        the type of the Stop at the Station
     * @return the found Stop
     */
    public Stop findStopByTypeAtStationByName(String stationName, StopType type) {
        Optional<Stop> optionalStop = this.stopRepository.findStopByTypeAtStationByName(stationName, type);

        return optionalStop.orElseThrow(() -> new StopNotFoundException(stationName, type));
    }

    /**
     * <p>Finds a specific stop type at a station with a given id
     * </p>
     *
     * @param stationId the id of the Station
     * @param type      the type of the Stop at the Station
     * @return the found Stop
     */
    public Stop findStopByTypeAtStationById(Long stationId, StopType type) {
        Optional<Stop> optionalStop = this.stopRepository.findStopByTypeAtStationById(stationId, type);

        return optionalStop.orElseThrow(() -> new StopNotFoundException(stationId, type));
    }

}
