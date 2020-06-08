package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.Schedule;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.ScheduleDto;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.service.*;
import org.neo4j.ogm.config.ObjectMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private StationService stationService;
    private StopService stopService;
    private LineService lineService;
    private LegService legService;

    private DtoConversionService conversionService;

    @Autowired
    public ScheduleController(StationService stationService, StopService stopService, LineService lineService,
                              LegService legService, DtoConversionService conversionService){
        this.stationService = stationService;
        this.stopService = stopService;
        this.lineService = lineService;
        this.legService = legService;
        this.conversionService = conversionService;
    }

    @PostMapping(path = "/importtest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> testImportSchedule(@RequestBody Schedule schedule){

        return ResponseEntity.ok(schedule);
    }

    @PostMapping(path = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> importSchedule(@Valid @RequestBody ScheduleDto scheduleDto){
        this.stationService.deleteAllStations();
        this.stopService.deleteAllStops();
        this.lineService.deleteAllLines();
        this.legService.deleteAllLegs();

        scheduleDto.getStationDTOs().forEach(stationDto -> {
            //Station newStation = conversionService.convert(stationDto, Station.class);
            Station newStation = this.conversionService.convert(stationDto);
            this.stationService.saveStation(newStation);
        });

        scheduleDto.getLineDTOs().forEach(lineDto -> {
            //Line newLine = conversionService.convert(lineDto, Line.class);
            Line newLine = this.conversionService.convert(lineDto);
            this.lineService.saveLine(newLine);
        });

        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping(path = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> exportSchedule(){
        ScheduleDto scheduleDto = new ScheduleDto();

        // Convert all Stations to StationDTOs and collect them
        ArrayList<StationDto> stationDtos = this.stationService.findAllStationsWithStops()
                .stream()
                .map(station -> this.conversionService.convert(station))
                .collect(Collectors.toCollection(ArrayList::new));

        // Convert all Lines to LineDTOs and collect them
        ArrayList<LineDto> lineDtos = this.lineService.findAllLinesWithLegs().stream()
                .map(line -> this.conversionService.convert(line))
                .collect(Collectors.toCollection(ArrayList::new));

        scheduleDto.setStations(stationDtos);
        scheduleDto.setLines(lineDtos);

        return ResponseEntity.ok(scheduleDto);
    }
}
