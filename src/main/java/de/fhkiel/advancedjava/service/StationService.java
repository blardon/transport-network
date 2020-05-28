package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.repository.StationRepository;
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

    @Autowired
    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
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
        return this.stationRepository.findById(id, 2).orElseThrow();
    }

    public Station findStationByName(String name){
        // TODO: Provide custom exception
        Optional<Station> optionalStation = this.stationRepository.findStationByName(name);
        return optionalStation.orElseThrow();
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
        if (optionalStation.isEmpty()){
            station.setState(AccessState.CLOSED);
            return this.saveStationWithStops(station);
        }
        throw new NoSuchElementException("This station id already exists");
    }

    public Iterable<Station> findAllStationsWithStops(){
        return this.stationRepository.findAll(2);
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
