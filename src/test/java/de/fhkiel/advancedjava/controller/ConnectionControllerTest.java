package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConnectionControllerTest {


    @Mock
    LegService legService;

    @Mock
    StatisticsService statisticsService;

    @Mock
    DtoConversionService dtoConversionService;

    @Mock
    StationService stationService;

    @InjectMocks
    ConnectionController connectionController;

    @Test
    void testBuyTicketsForLeg_valid(){
        //given
        Leg leg = new Leg();
        leg.setCost(BigDecimal.valueOf(5.55));

        when(legService.findLegByTypeBetweenStations(any(), anyString(), anyString())).thenReturn(leg);

        //when
        ResponseEntity<Object> response = connectionController.buyTicketsForLeg(10L, "From", "To", StopType.BUS);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(legService, times(1)).findLegByTypeBetweenStations(StopType.BUS, "From", "To");
        verify(statisticsService, times(1)).addTicketBought(leg, 10L);

        verifyNoMoreInteractions(legService);
        verifyNoMoreInteractions(statisticsService);
    }

    @Test
    void testBuyTicketsForLeg_invalid(){
        //given
        Leg leg = new Leg();
        leg.setCost(BigDecimal.valueOf(5.55));

        when(legService.findLegByTypeBetweenStations(any(), anyString(), anyString())).thenReturn(leg);

        //when
        assertThrows(WrongInputException.class, () -> {connectionController.buyTicketsForLeg(22L, "From", "To", StopType.BUS);});

    }

}
