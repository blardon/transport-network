package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.node.dto.LegDto;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.ScheduleDto;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.LineService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    private GenericConversionService conversionService;

    @Autowired
    public ScheduleController(StationService stationService, StopService stopService, LineService lineService,
                              LegService legService, GenericConversionService conversionService){
        this.stationService = stationService;
        this.stopService = stopService;
        this.lineService = lineService;
        this.legService = legService;
        this.conversionService = conversionService;
 }

    @PostMapping(path = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> importSchedule(@RequestBody ScheduleDto scheduleDto){
        this.stationService.deleteAllStations();
        this.stopService.deleteAllStops();
        this.lineService.deleteAllLines();
        this.legService.deleteAllLegs();

        scheduleDto.getStationDTOs().forEach(stationDto -> {
            Station newStation = conversionService.convert(stationDto, Station.class);
            this.stationService.saveStation(newStation);
        });

        scheduleDto.getLineDTOs().forEach(lineDto -> {
            Line newLine = conversionService.convert(lineDto, Line.class);
            this.lineService.saveLine(newLine);
        });

        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping(path = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> exportSchedule(){
        ScheduleDto scheduleDto = new ScheduleDto();

        // Get all Stations with their Stops from the StationService
        List<Station> allStationsWithStops = StreamSupport
                .stream(this.stationService.findAllStationsWithStops().spliterator(), false)
                .collect(Collectors.toList());

        // Get all Lines with their Legs from the LineService
        List<Line> allLinesWithStations = StreamSupport
                .stream(this.lineService.findAllLinesWithLegs().spliterator(), false)
                .collect(Collectors.toList());

        // Convert all Stations to StationDTOs and collect them
        ArrayList<StationDto> stationDTOs = allStationsWithStops.stream()
                .map(station -> conversionService.convert(station, StationDto.class))
                .collect(Collectors.toCollection(ArrayList::new));

        // Convert all Lines to LineDTOs and collect them
        ArrayList<LineDto> lineDtos = allLinesWithStations.stream()
                .map(line -> conversionService.convert(line, LineDto.class))
                .collect(Collectors.toCollection(ArrayList::new));

        scheduleDto.setStations(stationDTOs);
        scheduleDto.setLines(lineDtos);

        return ResponseEntity.ok(scheduleDto);
    }
}
