package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.StopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StationToStationDtoTest {

    @Mock
    private StopService stopService;

    private final DtoConversionService converter = new DtoConversionService(stopService);

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
        assertEquals(2, stationDto.getTypes().size());
        assertEquals(stationDto.getTransferTime(), station.getStops().get(0).getTransferTo().getTime());
        assertEquals(stationDto.getTransferTime(), station.getStops().get(1).getTransferTo().getTime());
        assertIterableEquals(stationDto.getTypes(), List.of(station.getStops().get(0).getType(), station.getStops().get(1).getType()));

    }

}
