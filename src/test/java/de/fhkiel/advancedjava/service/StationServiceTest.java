package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.StationNotFoundException;
import de.fhkiel.advancedjava.exception.StationServiceException;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.repository.StationRepository;
import de.fhkiel.advancedjava.repository.TransferToRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @DisplayName("when the service finds a station by name it is returned")
    void testFindStationByNameWithStops_valid() {
        // given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.findStationByNameWithStops("TestName")).thenReturn(Optional.of(station));

        //when
        Station result = stationService.findStationByNameWithStops("TestName");

        //then
        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).findStationByNameWithStops("TestName");
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    @DisplayName("when the service finds a station by name it is returned")
    void testFindStationByNameWithStops_invalid() {
        when(stationRepository.findStationByNameWithStops("TestName")).thenReturn(Optional.empty());

        assertThrows(StationNotFoundException.class, () -> {stationService.findStationByNameWithStops("TestName");});

        verify(stationRepository, times(1)).findStationByNameWithStops("TestName");
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

        //when
        Station result = stationService.findStationById(1L);

        //then
        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).findById(1L, 2);
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testFindStationByID_invalid(){
        when(stationRepository.findById(1L, 2)).thenReturn(Optional.empty());

        assertThrows(StationNotFoundException.class, () -> {stationService.findStationById(1L);});

        verify(stationRepository, times(1)).findById(1L, 2);
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testSaveStation_valid(){
        //given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.save(station)).thenReturn(station);

        //when
        Station result = stationService.saveStation(station);

        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).save(station);
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testSaveStation_invalid(){
        assertThrows(StationServiceException.class, () -> {stationService.saveStation(null);});

        verify(stationRepository, times(0)).save(any());
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testSaveStationWithStops_valid(){
        //given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        Stop stop = new Stop();
        stop.setType(StopType.SUBWAY);
        TransferTo transferTo = new TransferTo();
        transferTo.setTime(5L);
        transferTo.setToStation(station);
        transferTo.setFromStop(stop);
        stop.setTransferTo(transferTo);
        station.setStops(new ArrayList<>(List.of(stop)));

        when(stationRepository.save(station, 2)).thenReturn(station);

        //when
        Station result = stationService.saveStationWithStops(station);

        assertNotNull(result);
        assertEquals(station, result);

        verify(stationRepository, times(1)).save(station, 2);
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testSaveStationWithStops_invalid(){
        assertThrows(StationServiceException.class, () -> {stationService.saveStationWithStops(null);});

        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    void testFindAllStations(){
        // given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.findAll()).thenReturn(List.of(station));

        //when
        Collection<Station> result = stationService.findAllStations();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(stationRepository, times(1)).findAll();
    }

    @Test
    void testFindAllStationsWithStops(){
        // given
        Station station = new Station();
        station.setState(AccessState.OPENED);
        station.setStops(new ArrayList<>());
        station.setCity("TestCity");
        station.setName("TestName");
        station.setStationId(1L);

        when(stationRepository.findAll(2)).thenReturn(List.of(station));

        //when
        Collection<Station> result = stationService.findAllStationsWithStops();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(stationRepository, times(1)).findAll(2);
    }
}
