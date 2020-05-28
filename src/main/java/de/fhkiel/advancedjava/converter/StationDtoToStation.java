package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StationDtoToStation implements Converter<StationDto, Station> {
    @Override
    public Station convert(StationDto stationDto) {
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
    }
}
