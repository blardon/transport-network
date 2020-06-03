package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StopNotFoundException;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class StopService {

    private StopRepository stopRepository;

    @Autowired
    public StopService(StopRepository stopRepository){
        this.stopRepository = stopRepository;
    }

    public void deleteAllStops(){
        this.stopRepository.deleteAll();
    }

    public void saveAllStops(Collection<Stop> stops){
        this.stopRepository.saveAll(stops);
    }

    public Stop findStopByTypeAtStationById(Long stationId, StopType type){
        Optional<Stop> optionalStop = this.stopRepository.findStopByTypeAtStationById(stationId, type);

        return optionalStop.orElseThrow( () -> new StopNotFoundException(stationId, type) );
    }

}
