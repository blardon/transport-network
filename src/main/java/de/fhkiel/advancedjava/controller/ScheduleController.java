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

    @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Station testIt(@RequestBody StationDto stationDto){
        Station newStation = this.conversionService.convert(stationDto, Station.class);
        return newStation;
    }

    @PostMapping(path = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDto> importSchedule(@RequestBody ScheduleDto scheduleDto){
        this.stationService.deleteAllStations();
        this.stopService.deleteAllStops();
        this.lineService.deleteAllLines();
        this.legService.deleteAllLegs();

        scheduleDto.getStationDTOs().forEach(stationDto -> {
            Station newStation = conversionService.convert(stationDto, Station.class);
            this.stationService.addNewStation(newStation);
        });

        scheduleDto.getLineDTOs().forEach(lineDto -> {
            Line newLine = conversionService.convert(lineDto, Line.class);
            this.lineService.addNewLine(newLine);
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
/*
    private LineDto convertToLineDTO(Line line){
        LineDto lineDto = new LineDto();

        lineDto.setType(line.getType());
        lineDto.setName(line.getName());
        lineDto.setLineId(line.getLineId());

        // Collect all legDtos for Legs in the Line
        ArrayList<LegDto> legDtos = line.getLegs().stream().map(leg -> {
            LegDto legDto = new LegDto();
            legDto.setCost(leg.getCost());
            legDto.setTime(leg.getConnectingTo().getTime());
            legDto.setBeginStopId(leg.getStop().getTransferTo().getToStation().getStationId());
            legDto.setEndStopId(leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());
            return legDto;
        }).collect(Collectors.toCollection(ArrayList::new));
        lineDto.setLegs(legDtos);

        return lineDto;
    }

    private Line convertToLineEntity(LineDto lineDto){
        Line newLine = new Line();

        newLine.setLineId(lineDto.getLineId());
        newLine.setName(lineDto.getName());
        newLine.setType(lineDto.getType());

        // Create a new leg entity for every section in the line
        lineDto.getLegDTOs().forEach(legDto -> {
            Leg newLeg = new Leg();
            Stop fromStop = this.stopService.findStopByTypeAtStationById(legDto.getBeginStopId(), lineDto.getType());

            newLeg.setState(AccessState.OPENED);
            newLeg.setCost(legDto.getCost());
            newLeg.setStop(fromStop);

            ConnectingTo connectingTo = new ConnectingTo();
            Stop toStop = this.stopService.findStopByTypeAtStationById(legDto.getEndStopId(), lineDto.getType());
            connectingTo.setTime(legDto.getTime());
            connectingTo.setConnectingWithLeg(newLeg);
            connectingTo.setConnectingToStop(toStop);

            newLeg.setConnectingTo(connectingTo);

            newLine.getLegs().add(newLeg);
        });

        return newLine;
    }

    private StationDto convertToStationDTO(Station station){
        StationDto stationDto = new StationDto();

        stationDto.setCity(station.getCity());
        stationDto.setName(station.getName());
        stationDto.setState(station.getState());
        stationDto.setStationId(station.getStationId());

        ArrayList<StopType> types = station.getStops().stream()
                .map(stop -> {
                    stationDto.setTransferTime(stop.getTransferTo().getTime());
                    return stop.getType();
                }).collect(Collectors.toCollection(ArrayList::new));
        stationDto.setTypes(types);

        return stationDto;
    }

    private Station convertToStationEntity(StationDto stationDto){
        Station newStation = new Station();

        newStation.setStationId(stationDto.getStationId());
        newStation.setCity(stationDto.getCity());
        newStation.setName(stationDto.getName());
        newStation.setState(stationDto.getState());

        // Create a new stop entity and transfer relationship for every StopType in the station
        stationDto.getTypes().forEach(stopType -> {
            Stop newStop = new Stop();
            TransferTo transferTo = new TransferTo();

            newStop.setType(stopType);

            transferTo.setFromStop(newStop);
            transferTo.setToStation(newStation);
            transferTo.setTime(stationDto.getTransferTime());
            newStop.setTransferTo(transferTo);

            newStation.getStops().add(newStop);
        });
        return newStation;
    }*/

}
