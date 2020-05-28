package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.repository.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
