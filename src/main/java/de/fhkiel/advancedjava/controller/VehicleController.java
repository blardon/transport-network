package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.VehicleDto;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private VehicleService vehicleService;
    private StopService stopService;
    private LineService lineService;
    private DtoConversionService dtoConversionService;

    @Autowired
    public VehicleController(VehicleService vehicleService, StopService stopService, LineService lineService, DtoConversionService dtoConversionService) {
        this.vehicleService = vehicleService;
        this.stopService = stopService;
        this.lineService = lineService;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<Collection<VehicleDto>> getAllVehicles(){
        ArrayList<VehicleDto> vehicleDtos = this.vehicleService.findAll().stream().map(vehicle -> this.dtoConversionService.convert(vehicle)).collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(vehicleDtos);
    }

    @PutMapping(path = "/new/{atStationName}/{withLineName}")
    public ResponseEntity<VehicleDto> addNewVehicle(@PathVariable String atStationName, @PathVariable String withLineName){
        Line line = this.lineService.findLineByName(withLineName);
        Stop stop = this.stopService.findStopByTypeAtStationByName(atStationName, line.getType());

        Vehicle newVehicle = this.vehicleService.addNewVehicle(stop, line);
        return ResponseEntity.ok(this.dtoConversionService.convert(newVehicle));
    }

    @PutMapping(path = "/update/{id}/{atStationName}")
    public ResponseEntity<VehicleDto> updateVehicleLocation(@PathVariable Long id, @PathVariable String atStationName){
        Vehicle vehicle = this.vehicleService.findById(id);
        Stop stop = this.stopService.findStopByTypeAtStationByName(atStationName, vehicle.getServesForLine().getType());

        Vehicle newVehicle = this.vehicleService.updateVehicleStop(id, stop);
        return ResponseEntity.ok(this.dtoConversionService.convert(newVehicle));
    }
}
