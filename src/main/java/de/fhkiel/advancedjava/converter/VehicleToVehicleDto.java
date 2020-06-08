package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.VehicleDto;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehicleToVehicleDto  implements Converter<Vehicle, VehicleDto> {
    @Override
    public VehicleDto convert(Vehicle vehicle){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleId(vehicle.getId());
        vehicleDto.setServesForLineId(vehicle.getServesForLine().getLineId());
        return vehicleDto;
    }
}
