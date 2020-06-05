package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StationToStationDtoTest {

    private final StationToStationDto converter = new StationToStationDto();

    @Test
    void testConvertStationToStationDto(){
        //given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        Stop stop1 = new Stop();
        stop1.setType(StopType.BUS);

        Stop stop2 = new Stop();
        stop2.setType(StopType.SUBWAY);

        TransferTo transferTo1 = new TransferTo();
        transferTo1.setFromStop(stop1);
        transferTo1.setToStation(station);
        transferTo1.setTime(5L);

        TransferTo transferTo2 = new TransferTo();
        transferTo2.setFromStop(stop2);
        transferTo2.setToStation(station);
        transferTo2.setTime(5L);

        stop1.setTransferTo(transferTo1);
        stop2.setTransferTo(transferTo2);

        station.setStops(new ArrayList<>(List.of(stop1, stop2)));

        //when
        StationDto stationDto = converter.convert(station);

        //then
        assertNotNull(stationDto);
        assertEquals(stationDto.getCity(), station.getCity());
        assertEquals(stationDto.getName(), station.getName());
        assertEquals(stationDto.getState(), station.getState());
        assertEquals(stationDto.getStationId(), station.getStationId());
        assertEquals(stationDto.getTypes().size(), station.getStops().size());
        assertEquals(stationDto.getTypes().size(), 2);
        assertEquals(stationDto.getTransferTime(), station.getStops().get(0).getTransferTo().getTime());
        assertEquals(stationDto.getTransferTime(), station.getStops().get(1).getTransferTo().getTime());
        assertIterableEquals(stationDto.getTypes(), List.of(station.getStops().get(0).getType(), station.getStops().get(1).getType()));

    }

}
