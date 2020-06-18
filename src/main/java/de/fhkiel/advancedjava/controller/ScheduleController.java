package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.Schedule;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.ScheduleDto;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import de.fhkiel.advancedjava.service.*;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private StationService stationService;
    private StopService stopService;
    private LineService lineService;
    private LegService legService;
    private StatisticsService statisticsService;

    private DtoConversionService conversionService;

    @Autowired
    public ScheduleController(StationService stationService, StopService stopService, LineService lineService,
                              LegService legService, StatisticsService statisticsService, DtoConversionService conversionService){
        this.stationService = stationService;
        this.stopService = stopService;
        this.lineService = lineService;
        this.legService = legService;
        this.statisticsService = statisticsService;
        this.conversionService = conversionService;
    }

    @PostMapping(path = "/importtest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> testImportSchedule(@RequestBody Schedule schedule){

        return ResponseEntity.ok(schedule);
    }

    @PostMapping(path = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> importSchedule(@Valid @RequestBody ScheduleDto scheduleDto){
        this.statisticsService.deleteAll();
        this.stationService.deleteAllStations();
        this.stopService.deleteAllStops();
        this.lineService.deleteAllLines();
        this.legService.deleteAllLegs();

        scheduleDto.getStationDTOs().forEach(stationDto -> {
            Station newStation = this.conversionService.convert(stationDto);
            this.stationService.addNewStation(newStation);
        });

        scheduleDto.getLineDTOs().forEach(lineDto -> {
            Line newLine = this.conversionService.convert(lineDto);
            this.lineService.addNewLine(newLine);
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
