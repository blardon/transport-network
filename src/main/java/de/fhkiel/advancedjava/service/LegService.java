package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.LegNotFoundException;
import de.fhkiel.advancedjava.exception.LegServiceException;
import de.fhkiel.advancedjava.exception.StationServiceException;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class LegService {

    private LegRepository legRepository;

    @Autowired
    public LegService(LegRepository legRepository){
        this.legRepository = legRepository;
    }

    public void deleteAllLegs(){
        this.legRepository.deleteAll();
    }

    public Leg saveLeg(Leg leg){
        return this.legRepository.save(Optional.ofNullable(leg).orElseThrow( () -> new LegServiceException("Could not save leg.")));
    }

    public Collection<Leg> findAll(){
        final ArrayList<Leg> result = new ArrayList<>();
        this.legRepository.findAll().forEach(result::add);
        return result;
    }

    public Collection<Leg> findAllWithStopsAndStations(){
        final ArrayList<Leg> result = new ArrayList<>();
        this.legRepository.findAll(2).forEach(result::add);
        return result;
    }

    public Leg findById(Long legId){
        return this.legRepository.findById(legId, 2).orElseThrow( () -> new LegNotFoundException(legId) );
    }

    public Leg setLegOutOfOrder(Long legId, boolean set){
        Leg leg = this.findById(legId);

        if (set){
            leg.setState(AccessState.OUT_OF_ORDER);
        }else{
            leg.setState(AccessState.OPENED);
        }

        return this.saveLeg(leg);
    }

    public Leg findLegByTypeBetweenStations(StopType type, String fromStation, String toStation){
        return this.legRepository.findLegByTypeBetweenStations(type, fromStation, toStation).orElseThrow( () -> new LegNotFoundException(type, fromStation, toStation));
    }

}
