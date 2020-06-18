package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.VehicleNotFoundException;
import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        return this.vehicleRepository.save(Optional.ofNullable(vehicle).orElseThrow( () -> new WrongInputException("No vehicle supplied.")));
    }

    public Vehicle findById(Long id){
        return this.vehicleRepository.findById(id).orElseThrow( () -> new VehicleNotFoundException(id));
    }

    public Collection<Vehicle> findAll(){
        final Collection<Vehicle> vehicles = new ArrayList<>();
        this.vehicleRepository.findAll(3).forEach(vehicles::add);
        return vehicles;
    }

    public void deleteVehicle(Long id){
        this.vehicleRepository.deleteById(id);
    }

    public Vehicle addNewVehicle(Stop stop, Line line){
        Vehicle newVehicle = new Vehicle();
        newVehicle.setLocatedAtStop(stop);
        newVehicle.setServesForLine(line);
        return this.saveVehicle(newVehicle);
    }

    public Vehicle updateVehicleStop(Long vehicleId, Stop newStop){
        Vehicle vehicle = this.findById(vehicleId);
        vehicle.setLocatedAtStop(newStop);
        return this.saveVehicle(vehicle);
    }

}
