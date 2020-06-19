package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.LegServiceException;
import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.repository.LegRepository;
import de.fhkiel.advancedjava.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LegServiceTest {

    @Mock
    private LegRepository legRepository;

    @InjectMocks
    private LegService legService;

    @Test
    void testDeleteAllLegs(){
        legService.deleteAllLegs();

        verify(legRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(legRepository);
    }

    @Test
    void testSaveLeg_valid(){
        Leg leg = new Leg();

        when(legRepository.save(leg)).thenReturn(leg);

        //then
        Leg result = legService.saveLeg(leg);

        assertEquals(leg, result);

        verify(legRepository, times(1)).save(leg);
        verifyNoMoreInteractions(legRepository);
    }

    @Test
    void testSaveLeg_invalid(){
        assertThrows(LegServiceException.class, () -> {legService.saveLeg(null);});

        verify(legRepository, times(0)).save(any());
        verifyNoMoreInteractions(legRepository);
    }
    @Test
    void testFindAllLegs(){
        // given
        Leg leg = new Leg();

        when(legRepository.findAll()).thenReturn(List.of(leg));

        //when
        Collection<Leg> result = legService.findAll();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(leg));

        verify(legRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithStopsAndStations(){
        // given
        Leg leg = new Leg();

        when(legRepository.findAll(2)).thenReturn(List.of(leg));

        //when
        Collection<Leg> result = legService.findAllWithStopsAndStations();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(leg));

        verify(legRepository, times(1)).findAll(2);
    }

    @Test
    void testSetLegOutOfOrder_True(){
        //given
        Leg leg = new Leg();
        leg.setState(AccessState.OPENED);

        when(legRepository.save(leg)).thenReturn(leg);
        when(legRepository.findById(1L, 2)).thenReturn(Optional.of(leg));

        //when
        Leg result = legService.setLegOutOfOrder(1L, true);

        //then
        assertNotNull(result);
        assertEquals(AccessState.OUT_OF_ORDER, result.getState());

        verify(legRepository, times(1)).findById(1L, 2);
        verify(legRepository, times(1)).save(leg);
        verifyNoMoreInteractions(legRepository);
    }

    @Test
    void testSetLegOutOfOrder_False(){
        //given
        Leg leg = new Leg();
        leg.setState(AccessState.OUT_OF_ORDER);

        when(legRepository.save(leg)).thenReturn(leg);
        when(legRepository.findById(1L, 2)).thenReturn(Optional.of(leg));

        //when
        Leg result = legService.setLegOutOfOrder(1L, false);

        //then
        assertNotNull(result);
        assertEquals(AccessState.OPENED, result.getState());

        verify(legRepository, times(1)).findById(1L, 2);
        verify(legRepository, times(1)).save(leg);
        verifyNoMoreInteractions(legRepository);
    }

}
