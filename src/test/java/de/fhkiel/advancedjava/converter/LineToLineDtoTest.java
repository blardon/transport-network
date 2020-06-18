package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class LineToLineDtoTest {

    private final LineToLineDto converter = new LineToLineDto();

    @Test
    void testConvertLineToLineDto(){
        //given
        Line line = new Line();
        line.setType(StopType.BUS);
        line.setName("TestLine");
        line.setLineId(1L);

        Leg leg = new Leg();
        leg.setCost(BigDecimal.valueOf(2.25));
        leg.setState(AccessState.OPENED);

        Station toStation = new Station();
        toStation.setStationId(1L);
        toStation.setName("TestStation");
        toStation.setCity("TestCity");
        toStation.setState(AccessState.OPENED);

        Station fromStation = new Station();
        fromStation.setStationId(2L);
        fromStation.setName("TestStation2");
        fromStation.setCity("TestCity2");
        fromStation.setState(AccessState.OPENED);

        ConnectingTo connectingTo = new ConnectingTo();
        connectingTo.setConnectingWithLeg(leg);
        connectingTo.setTime(5L);

        TransferTo transferTo = new TransferTo();
        transferTo.setTime(3L);
        transferTo.setToStation(toStation);

        TransferTo transferTo2 = new TransferTo();
        transferTo2.setTime(2L);
        transferTo2.setToStation(fromStation);

        Stop connectingToStop = new Stop();
        connectingToStop.setType(StopType.BUS);
        connectingToStop.setTransferTo(transferTo);

        Stop fromStop = new Stop();
        fromStop.setType(StopType.BUS);
        fromStop.setTransferTo(transferTo2);

        connectingTo.setConnectingToStop(connectingToStop);
        transferTo.setFromStop(connectingToStop);
        transferTo2.setFromStop(fromStop);
        toStation.setStops(new ArrayList<>(List.of(connectingToStop)));
        fromStation.setStops(new ArrayList<>(List.of(fromStop)));
        leg.setConnectingTo(connectingTo);
        leg.setStop(fromStop);
        line.setLegs(new ArrayList<>(List.of(leg)));

        //when

        LineDto lineDto = converter.convert(line);

        //then
        assertNotNull(lineDto);
        assertNotNull(lineDto.getLegDTOs());
        assertEquals(lineDto.getLineId(), line.getLineId());
        assertEquals(lineDto.getName(), line.getName());
        assertEquals(lineDto.getType(), line.getType());
        assertEquals(lineDto.getLegDTOs().size(), 1);
        assertEquals(lineDto.getLegDTOs().get(0).getCost(), line.getLegs().get(0).getCost());
        assertEquals(lineDto.getLegDTOs().get(0).getTime(), line.getLegs().get(0).getConnectingTo().getTime());
        assertEquals(lineDto.getLegDTOs().get(0).getBeginStopId(), line.getLegs().get(0).getStop().getTransferTo().getToStation().getStationId());
        assertEquals(lineDto.getLegDTOs().get(0).getEndStopId(), line.getLegs().get(0).getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());

    }

}
