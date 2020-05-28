package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.node.dto.StopDto;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResultDto;
import de.fhkiel.advancedjava.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    private StationService stationService;
    private GenericConversionService conversionService;

    @Autowired
    public ConnectionController(StationService stationService, GenericConversionService conversionService){
        this.stationService = stationService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/from/{stationNameFrom}/to/{stationNameTo}")
    public ResponseEntity<Object> getFastestConnectionWithoutTransferTime
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        Iterable<Map<String, Object>> result = this.stationService.findFastestPathWithoutTransferTime(stationNameFrom, stationNameTo);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/from/{stationNameFrom}/to/{stationNameTo}/withTransferTime")
    public ResponseEntity<ConnectionResult> getFastestConnectionWithTransferTime
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        ConnectionResult result = this.stationService.findFastestPathWithTransferTime(stationNameFrom, stationNameTo);

        //ConnectionResultDto connectionResultDto = new ConnectionResultDto();
        //connectionResultDto.setStationDtos(result.getStations()
        //        .stream()
        //        .map(station -> conversionService.convert(station, StationDto.class))
        //        .collect(Collectors.toCollection(ArrayList::new)));

        return ResponseEntity.ok(result);
    }

}