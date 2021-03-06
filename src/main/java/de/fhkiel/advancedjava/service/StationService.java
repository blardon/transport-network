package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.ConnectionNotFoundException;
import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.exception.StationServiceException;
import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Manages stations.
 *
 * @author Bennet v. Lardon
 */
@Service
public class StationService {

    private StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void deleteAllStations() {
        this.stationRepository.deleteAll();
    }

    public Station saveStationWithStops(Station station) {
        return this.stationRepository.save(Optional.ofNullable(station).orElseThrow(() -> new StationServiceException("Could not save station.")), 2);
    }

    public Station saveStation(Station station) {
        return this.stationRepository.save(Optional.ofNullable(station).orElseThrow(() -> new StationServiceException("Could not save station.")));
    }

    public Station findStationById(Long id) {
        return this.stationRepository.findById(id, 2).orElseThrow(() -> new StationNotFoundException(id));
    }

    public Station findStationByName(String name) {
        return this.stationRepository.findStationByName(name).orElseThrow(() -> new StationNotFoundException(name));
    }

    public Station findStationByNameWithStops(String name) {
        return this.stationRepository.findStationByNameWithStops(name).orElseThrow(() -> new StationNotFoundException(name));
    }

    public Collection<Station> findAllStations() {
        final Collection<Station> stations = new ArrayList<>();
        this.stationRepository.findAll().forEach(stations::add);
        return stations;
    }

    public Collection<Station> findAllStationsWithStops() {
        final Collection<Station> stations = new ArrayList<>();
        this.stationRepository.findAll(2).forEach(stations::add);
        return stations;
    }

    /**
     * <p>Sets the transfer time for a station
     * </p>
     *
     * @param name the name of the Station
     * @param time the new transfer time for the station
     * @return the saved new Station
     */
    public Station setStationTransferTime(String name, Long time) {
        Station station = this.findStationByNameWithStops(name);

        if (time < 0) {
            throw new WrongInputException("Transfer time cannot be negative.");
        }

        station.getStops().forEach(stop ->
                stop.getTransferTo().setTime(time)
        );

        return this.saveStationWithStops(station);
    }

    /**
     * <p>Sets a Station out of order or or opens the Station
     * </p>
     *
     * @param name the name of the Station
     * @param set if true, the Station will be set to OUT_OF_ORDER, if false, it will be set to OPENED
     * @return the saved new Station
     */
    public Station setStationOutOfOrder(String name, boolean set) {
        Station station = this.findStationByName(name);
        if (set) {
            station.setState(AccessState.OUT_OF_ORDER);
        } else {
            station.setState(AccessState.OPENED);
        }
        return this.saveStation(station);
    }

    /**
     * <p>Adds a new Station to the schedule in the database
     * </p>
     *
     * @param station the new Station
     * @return the saved new Station
     */
    public Station addNewStation(Station station) {
        Optional<Station> optionalStation = this.stationRepository.findById(station.getStationId());
        Optional<Station> optionalStationName = this.stationRepository.findStationByName(station.getName());

        if (optionalStation.isPresent()) {
            throw new WrongInputException(String.format("Station with ID %d already exists.", station.getStationId()));
        }

        if (optionalStationName.isPresent()) {
            throw new WrongInputException(String.format("Station with name %s already exists.", station.getName()));
        }

        return this.saveStationWithStops(station);
    }

    /**
     * <p>Finds the fastest connection between two Stations
     * </p>
     *
     * @param from The name of the Station where the connection should begin
     * @param to   The name of the Station where the connection should end
     * @return the found ConnectionResult
     */
    public ConnectionResult findFastestPathWithTransferTime(String from, String to) {
        Optional<ConnectionResult> optionalConnectionResult = this.stationRepository.findFastestPathWithTransferTime(from, to);

        if (optionalConnectionResult.isEmpty()) {
            throw new ConnectionNotFoundException(from, to);
        }

        ConnectionResult result = optionalConnectionResult.get();

        // Remove last transfer time, depending if it should be included or not
        if (result.getStops() != null && result.getStops().size() > 1) {
            TransferTo lastTransfer = result.getStops().get(result.getStops().size() - 1).getTransferTo();
            result.setTotalTime(result.getTotalTime() - lastTransfer.getTime());
        }

        // Calculate costs for the connection
        BigDecimal totalCost = result.getLegs().stream().map(Leg::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setTotalCost(totalCost);

        return result;
    }

    /**
     * <p>Finds a connection with min. nStops in max. mMinutes
     * </p>
     *
     * @param nStops   How many stops should be visited min.
     * @param mMinutes How many minutes should the connection take max.
     * @return the found ConnectionResult
     */
    public ConnectionResult findNStopsInMMinutes(Long nStops, Long mMinutes) {
        Optional<ConnectionResult> optionalConnectionResult = this.stationRepository.findNStopsInMMinutes(nStops, mMinutes);

        if (optionalConnectionResult.isEmpty()) {
            throw new ConnectionNotFoundException(nStops, mMinutes);
        }

        ConnectionResult result = optionalConnectionResult.get();

        // Calculate costs for the connection
        BigDecimal totalCost = result.getLegs().stream().map(Leg::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setTotalCost(totalCost);

        return result;
    }

    /**
     * <p>Finds the 3 cheapest connections between two Stations
     * </p>
     *
     * @param from The name of the Station where the connection should begin
     * @param to   The name of the Station where the connection should end
     * @return the max. 3 found ConnectionResults
     */
    public Collection<ConnectionResult> find3Cheapest(String from, String to) {
        final Collection<ConnectionResult> connectionResults = new ArrayList<>();
        this.stationRepository.find3LowestCosts(from, to).forEach(connectionResults::add);

        if (connectionResults.isEmpty()) {
            throw new ConnectionNotFoundException(from, to);
        }

        // Calculate costs for the connections
        connectionResults.forEach(result -> {
            BigDecimal totalCost = result.getLegs().stream().map(Leg::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            result.setTotalCost(totalCost);
        });

        // Calculate total time for the connections
        connectionResults.forEach(connectionResult -> {
            Long totalTransferTime = connectionResult.getTransfers().stream().mapToLong(TransferTo::getTime).sum();
            Long totalConnectionTime = connectionResult.getConnections().stream().mapToLong(ConnectingTo::getTime).sum();
            connectionResult.setTotalTime(totalTransferTime + totalConnectionTime);
        });

        return connectionResults;
    }

}
