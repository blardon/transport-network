package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.LineNotFoundException;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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

    public Line addNewLine(Line line){
        Optional<Line> optionalLine = this.lineRepository.findById(line.getLineId(), 3);

        if (optionalLine.isEmpty()){
            return this.saveLine(line);
        }else{
            return optionalLine.get();
        }
    }

    public Iterable<Line> saveAllLines(Collection<Line> lines){
        return this.lineRepository.save(lines, 2);
    }

    public Line findLineByName(String lineName){
        return this.lineRepository.findLineByName(lineName).orElseThrow( () -> new LineNotFoundException(lineName) );
    }

    public Collection<Line> findAllLinesWithLegs(){
        final Collection<Line> lines = new ArrayList<>();
        this.lineRepository.findAll(3).forEach(lines::add);
        return lines;
    }

}
