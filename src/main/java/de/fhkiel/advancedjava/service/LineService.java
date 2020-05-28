package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LineService {

    private LineRepository lineRepository;

    @Autowired
    public LineService(LineRepository lineRepository){
        this.lineRepository = lineRepository;
    }

    public void deleteAllLines(){
        this.lineRepository.deleteAll();
    }

    public Line saveLine(Line line){
        return this.lineRepository.save(line, 2);
    }

    public void addNewLine(Line line){
        if (!this.lineRepository.existsById(line.getLineId())){
            this.saveLine(line);
        }
    }

    public Iterable<Line> saveAllLines(Collection<Line> lines){
        return this.lineRepository.save(lines, 2);
    }

    public Iterable<Line> findAllLinesWithLegs(){
        return this.lineRepository.findAll(2);
    }

}
