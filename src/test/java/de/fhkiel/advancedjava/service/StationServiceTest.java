package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.repository.StationRepository;
import de.fhkiel.advancedjava.repository.TransferToRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TransferToRepository transferToRepository;

    @InjectMocks
    private StationService stationService;

    @Test
    void testDeleteAllStations(){
        stationService.deleteAllStations();

        verify(stationRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    @DisplayName("when the service saves a station it is returned")
    void testSaveStation(){
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.save(station)).thenReturn(station);

        //when
        Station result = stationService.saveStation(station);

        //then
        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository).save(station);
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    @DisplayName("when the service finds a station by name it is returned")
    void testFindStationByName_valid() {
        // given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.findStationByName(anyString())).thenReturn(Optional.of(station));

        //when
        Station result = stationService.findStationByName("TestName");

        //then
        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).findStationByName("TestName");
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    @DisplayName("when the service finds a station by name exception is thrown")
    void testFindStationByName_invalid(){
        when(stationRepository.findStationByName(anyString())).thenReturn(Optional.empty());

        assertThrows(StationNotFoundException.class, () -> {stationService.findStationByName("TestName");});

        verify(stationRepository, times(1)).findStationByName("TestName");
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    @DisplayName("when the service finds a station by id it is returned")
    void testFindStationByID_valid(){
        // given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.findById(1L, 2)).thenReturn(Optional.of(station));

        Station result = stationService.findStationById(1L);

        //then
        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).findById(1L, 2);
        verifyNoMoreInteractions(stationRepository);
    }
}
