package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.dto.LegDto;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.LineService;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LineController {

    private LineService lineService;
    private LegService legService;
    private StatisticsService statisticsService;
    private DtoConversionService conversionService;

    @Autowired
    public LineController(LineService lineService, LegService legService, StatisticsService statisticsService, DtoConversionService conversionService){
        this.lineService = lineService;
        this.legService = legService;
        this.statisticsService = statisticsService;
        this.conversionService = conversionService;
    }

    @PostMapping(path = "/line/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LineDto> addNewLine(@RequestBody LineDto lineDto){
        Line newLine = this.conversionService.convert(lineDto);

        this.lineService.addNewLine(newLine);

        return ResponseEntity.ok(lineDto);
    }

    @GetMapping(path = "/leg/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LegDto>> getAllLegs(){
        Collection<Leg> legs = this.legService.findAllWithStopsAndStations();

        List<LegDto> legDtos = legs.stream().map(leg -> this.conversionService.convert(leg)).collect(Collectors.toList());
        return ResponseEntity.ok(legDtos);
    }

    @PutMapping(path = "/leg/{id}/set/outoforder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LegDto> setLegOutOfOrder(@PathVariable Long id){
        Leg newLeg = this.legService.setLegOutOfOrder(id, true);
        this.statisticsService.addDisturbanceCreated(newLeg);

        LegDto response = this.conversionService.convert(newLeg);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/leg/{id}/resolve/outoforder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LegDto> resolveLegOutOfOrder(@PathVariable Long id){
        Leg newLeg = this.legService.setLegOutOfOrder(id, false);

        LegDto response = this.conversionService.convert(newLeg);
        return ResponseEntity.ok(response);
    }

}
