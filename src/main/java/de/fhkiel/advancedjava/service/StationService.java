package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.exception.StationServiceException;
import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StationService {

    private StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    public void deleteAllStations(){
        this.stationRepository.deleteAll();
    }

    public Station saveStationWithStops(Station station){
        return this.stationRepository.save(Optional.ofNullable(station).orElseThrow( () -> new StationServiceException("Could not save station.")), 2);
    }

    public Station saveStation(Station station){
        return this.stationRepository.save(Optional.ofNullable(station).orElseThrow( () -> new StationServiceException("Could not save station.")));
    }

    public Station findStationById(Long id){
        return this.stationRepository.findById(id, 2).orElseThrow( () -> new StationNotFoundException(id) );
    }

    public Station findStationByName(String name){
        return this.stationRepository.findStationByName(name).orElseThrow( () -> new StationNotFoundException(name));
    }

    public Station findStationByNameWithStops(String name){
        return this.stationRepository.findStationByName(name, 2).orElseThrow( () -> new StationNotFoundException(name));
    }

    public Collection<Station> findAllStations(){
        final Collection<Station> stations = new ArrayList<>();
        this.stationRepository.findAll().forEach(stations::add);
        return stations;
    }

    public Collection<Station> findAllStationsWithStops(){
        final Collection<Station> stations = new ArrayList<>();
        this.stationRepository.findAll(2).forEach(stations::add);
        return stations;
    }

    public Station setStationTransferTime(String name, Long time){
        Station station = this.findStationByNameWithStops(name);

        if (time < 0){
            throw new WrongInputException("Transfer time cannot be negative.");
        }

        station.getStops().forEach(stop -> {
            stop.getTransferTo().setTime(time);
        });

        /*Collection<TransferTo> transferTos = this.transferToRepository.findAllToStationByStationName(name);
        ArrayList<TransferTo> newTransferTos = transferTos.stream()
                .peek(transferTo -> transferTo.setTime(time))
                .collect(Collectors.toCollection(ArrayList::new));
        this.transferToRepository.saveAll(newTransferTos);*/

        return this.saveStationWithStops(station);
    }

    public Station setStationOutOfOrder(String name, boolean set){
        Station station = this.findStationByName(name);
        if (set){
            station.setState(AccessState.OUT_OF_ORDER);
        }else{
            station.setState(AccessState.OPENED);
        }
        return this.saveStation(station);
    }

    public Station addNewStation(Station station){
        Optional<Station> optionalStation = this.stationRepository.findById(station.getStationId());

        if (optionalStation.isPresent()){
            throw new WrongInputException(String.format("Station with ID %d already exists.", station.getStationId()));
        }

        station.setState(AccessState.CLOSED);
        return this.saveStationWithStops(station);
    }

    public Iterable<Map<String, Object>> findFastestPathWithoutTransferTime(String from, String to){
        // TODO: REFINE RETURN OBJECT
        Iterable<Map<String, Object>> result = this.stationRepository.findFastestPathWithoutTransferTime(from, to);

        return null;
    }

    public ConnectionResult findFastestPathWithTransferTime(String from, String to){
        ConnectionResult result = this.stationRepository.findFastestPathWithTransferTime(from, to);

        // Remove last transfer, depending if it should be included or not
        //if (result.getStations() != null && result.getStations().size() > 0){
        //    Station lastStation = result.getStations().get(result.getStations().size() - 1);
        //    if (lastStation.getStops() != null && lastStation.getStops().size() > 0)
        //    lastStation.getStops().get(0).setTransferTo(null);
        //}

        result.hashCode();
        return result;
    }

}
