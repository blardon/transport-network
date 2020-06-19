package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.schedule.*;
import de.fhkiel.advancedjava.model.schedule.dto.LegDto;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.StopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineDtoToLineTest {

    @Mock
    private StopService stopService;

    @InjectMocks
    private DtoConversionService converter;

    @Test
    void testConvertLineDtoToLine(){
        //given
        LineDto lineDto = new LineDto();
        lineDto.setLineId(1L);
        lineDto.setName("TestLine");
        lineDto.setType(StopType.BUS);
        lineDto.setLegs(new ArrayList<>());


        LegDto legDto = new LegDto();
        legDto.setState(AccessState.OPENED);
        legDto.setLegId(25L);
        legDto.setBeginStopId(2L);
        legDto.setEndStopId(3L);
        legDto.setCost(BigDecimal.ONE);
        legDto.setTime(5L);

        lineDto.getLegDTOs().add(legDto);

        Stop stop1 = new Stop();
        stop1.setType(StopType.BUS);

        Stop stop2 = new Stop();
        stop2.setType(StopType.BUS);

        when(stopService.findStopByTypeAtStationById(2L, StopType.BUS)).thenReturn(stop1);
        when(stopService.findStopByTypeAtStationById(3L, StopType.BUS)).thenReturn(stop2);

        //when
        Line line = converter.convert(lineDto);

        //then
        assertNotNull(line);
        assertEquals(line.getLineId(), lineDto.getLineId());
        assertEquals(line.getName(), lineDto.getName());
        assertEquals(line.getType(), lineDto.getType());
        assertEquals(1, line.getLegs().size());
        assertNotNull(line.getLegs().get(0).getConnectingTo());
        assertNotNull(line.getLegs().get(0).getStop());
        assertEquals(line.getLegs().get(0).getStop(), stop1);
        assertEquals(line.getLegs().get(0).getCost(), legDto.getCost());
        assertEquals(line.getLegs().get(0).getState(), legDto.getState());
        assertEquals(line.getLegs().get(0).getConnectingTo().getTime(), legDto.getTime());
        assertEquals(line.getLegs().get(0).getConnectingTo().getConnectingWithLeg(), line.getLegs().get(0));
        assertNotNull(line.getLegs().get(0).getConnectingTo().getConnectingToStop());
        assertEquals(line.getLegs().get(0).getConnectingTo().getConnectingToStop(), stop2);


    }

}
