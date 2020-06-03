package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/station")
public class StationController {

    private StationService stationService;
    private DtoConversionService conversionService;

    @Autowired
    public StationController(StationService stationService, DtoConversionService conversionService){
        this.stationService = stationService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StationDto> findStation(@PathVariable Long id){
        Station station = this.stationService.findStationById(id);

        StationDto response = this.conversionService.convert(station);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StationDto> addNewStation(@Valid @RequestBody StationDto stationDto){

        //Station newStation = conversionService.convert(stationDto, Station.class);
        Station newStation = this.conversionService.convert(stationDto);
        //if (newStation == null){
        //    return ResponseEntity.badRequest().body(stationDto);
        //}

        Station savedStation = this.stationService.addNewStation(newStation);

        //StationDto response = this.conversionService.convert(savedStation, StationDto.class);
        StationDto response = this.conversionService.convert(savedStation);

        if (response == null){
            return ResponseEntity.badRequest().body(stationDto);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{name}/set/transfertime/{time}")
    public ResponseEntity<StationDto> setStationTransferTime(@PathVariable String name, @PathVariable Long time){
        Station atStation = this.stationService.setStationTransferTime(name, time);

        //StationDto response = this.conversionService.convert(atStation, StationDto.class);
        StationDto response = this.conversionService.convert(atStation);

        if (response == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{name}/set/outoforder")
    public ResponseEntity<StationDto> setStationOutOfOrder(@PathVariable String name){
        Station newStation = this.stationService.setStationOutOfOrder(name, true);

        //StationDto response = this.conversionService.convert(newStation, StationDto.class);
        StationDto response = this.conversionService.convert(newStation);

        if (response == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{name}/resolve/outoforder")
    public ResponseEntity<StationDto> resolveStationOutOfOrder(@PathVariable String name){
        Station newStation = this.stationService.setStationOutOfOrder(name, false);

        //StationDto response = this.conversionService.convert(newStation, StationDto.class);
        StationDto response = this.conversionService.convert(newStation);

        if (response == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

}
