package de.fhkiel.advancedjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.ScheduleDto;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import de.fhkiel.advancedjava.service.*;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ScheduleControllerTest {

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private StationService stationService;

    @Mock
    private StopService stopService;

    @Mock
    private LineService lineService;

    @Mock
    private LegService legService;

    @Mock
    private DtoConversionService conversionService;

    @InjectMocks
    private ScheduleController scheduleController;

    private ObjectMapper objectMapper;

    @Autowired
    public ScheduleControllerTest(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Test
    void testImportSchedule_valid() throws IOException {
        //given
        File importFile = new File(getClass().getClassLoader().getResource("import.json").getFile());
        ScheduleDto scheduleDto = objectMapper.readValue(importFile, ScheduleDto.class);

        when(stationService.addNewStation(any())).thenReturn(null);
        when(lineService.addNewLine(any())).thenReturn(null);

        // when
        ResponseEntity<ScheduleDto> response = scheduleController.importSchedule(scheduleDto);

        //then
        assertNotNull(scheduleDto);
        assertNotNull(response.getBody());

        // Verify deletion
        verify(statisticsService, times(1)).deleteAll();
        verify(stationService, times(1)).deleteAllStations();
        verify(stopService, times(1)).deleteAllStops();
        verify(lineService, times(1)).deleteAllLines();
        verify(legService, times(1)).deleteAllLegs();

        // Verify conversion
        verify(conversionService, times(scheduleDto.getStationDTOs().size())).convert(any(StationDto.class));
        verify(conversionService, times(scheduleDto.getLineDTOs().size())).convert(any(LineDto.class));

        // Verify saving
        verify(stationService, times(scheduleDto.getStationDTOs().size())).addNewStation(any());
        verify(lineService, times(scheduleDto.getLineDTOs().size())).addNewLine(any());

        verifyNoMoreInteractions(statisticsService);
        verifyNoMoreInteractions(stationService);
        verifyNoMoreInteractions(stopService);
        verifyNoMoreInteractions(lineService);
        verifyNoMoreInteractions(legService);
        verifyNoMoreInteractions(conversionService);
    }

}
