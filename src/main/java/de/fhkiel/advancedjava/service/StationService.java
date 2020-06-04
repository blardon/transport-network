package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.repository.StationRepository;
import de.fhkiel.advancedjava.repository.TransferToRepository;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StationService {

    private StationRepository stationRepository;
    private TransferToRepository transferToRepository;

    @Autowired
    public StationService(StationRepository stationRepository, TransferToRepository transferToRepository){
        this.stationRepository = stationRepository;
        this.transferToRepository = transferToRepository;
    }

    public void deleteAllStations(){
        this.stationRepository.deleteAll();
    }

    public Station saveStationWithStops(Station station){
        return this.stationRepository.save(station, 2);
    }

    public Station saveStation(Station station){
        return this.stationRepository.save(station);
    }

    public Station findStationById(Long id){
        return this.stationRepository.findById(id, 2).orElseThrow( () -> new StationNotFoundException(id) );
    }

    public Station findStationByName(String name){
        return this.stationRepository.findStationByName(name).orElseThrow( () -> new StationNotFoundException(name));
    }

    public Station setStationTransferTime(String name, Long time){
        Optional<Station> station = this.stationRepository.findStationByName(name, 2);
        if (station.isEmpty()){
            throw new StationNotFoundException(name);
        }

        if (time < 0){
            throw new WrongInputException("Transfer time cannot be negative.");
        }

        Collection<TransferTo> transferTos = this.transferToRepository.findAllToStationByStationName(name);
        ArrayList<TransferTo> newTransferTos = transferTos.stream()
                .peek(transferTo -> transferTo.setTime(time))
                .collect(Collectors.toCollection(ArrayList::new));
        this.transferToRepository.saveAll(newTransferTos);

        return station.get();
    }

    public Station setStationOutOfOrder(String name, boolean set){
        Station station = this.findStationByName(name);
        if (set){
            station.setState(AccessState.CLOSED);
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

    public Collection<Station> findAllStationsWithStops(){
        final Collection<Station> stations = new ArrayList<>();
        this.stationRepository.findAll(2).forEach(stations::add);
        return stations;
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
