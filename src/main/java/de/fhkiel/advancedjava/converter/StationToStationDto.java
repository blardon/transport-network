package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class StationToStationDto implements Converter<Station, StationDto> {
    @Override
    public StationDto convert(Station station) {
        StationDto stationDto = new StationDto();

        stationDto.setCity(station.getCity());
        stationDto.setName(station.getName());
        stationDto.setState(station.getState());
        stationDto.setStationId(station.getStationId());

        // Set types array with all types that are possible for the stop
        ArrayList<StopType> types = station.getStops().stream()
                .map(Stop::getType).collect(Collectors.toCollection(ArrayList::new));
        stationDto.setTypes(types);

        // Set transfer time if set (not null)
        station.getStops().forEach(stop -> {
            if (stop.getTransferTo() != null){
                stationDto.setTransferTime(stop.getTransferTo().getTime());
            }
        });


        return stationDto;
    }
}
