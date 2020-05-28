package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    public void saveStation(Station station){
        this.stationRepository.save(station, 2);
    }

    public Station findStationById(Long id){
        return this.stationRepository.findById(id, 2).orElseThrow();
    }

    public void addNewStation(Station station){
        if (!this.stationRepository.existsById(station.getStationId())){
            if (station.getState() == null){
                station.setState(AccessState.CLOSED);
            }
            this.saveStation(station);
        }
    }

    public Iterable<Station> findAllStationsWithStops(){
        return this.stationRepository.findAll(2);
    }

    public void saveAllStations(Collection<Station> stations){
        this.stationRepository.save(stations, 2);
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
