package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/station")
public class StationController {

    private StationService stationService;
    private GenericConversionService conversionService;

    @Autowired
    public StationController(StationService stationService, GenericConversionService conversionService){
        this.stationService = stationService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<StationDto> findStation(@PathVariable Long id){
        Station station = this.stationService.findStationById(id);
        return ResponseEntity.ok(conversionService.convert(station, StationDto.class));
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StationDto> addNewStation(@RequestBody StationDto stationDto){

        Station newStation = conversionService.convert(stationDto, Station.class);

        if (newStation != null){
            this.stationService.addNewStation(newStation);
            return ResponseEntity.ok(stationDto);
        }
        return ResponseEntity.badRequest().body(stationDto);
    }

}
