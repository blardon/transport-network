package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.LegNotFoundException;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

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

    public Iterable<Leg> saveAllLegs(Collection<Leg> legs){
        return this.legRepository.saveAll(legs);
    }

    public Collection<Leg> findAll(){
        final ArrayList<Leg> result = new ArrayList<>();
        this.legRepository.findAll().forEach(result::add);
        return result;
    }

    public Leg findLegByTypeBetweenStations(StopType type, String fromStation, String toStation){
        return this.legRepository.findLegByTypeBetweenStations(type, fromStation, toStation).orElseThrow( () -> new LegNotFoundException(type, fromStation, toStation));
    }

}
