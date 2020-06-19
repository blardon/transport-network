package de.fhkiel.advancedjava.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhkiel.advancedjava.exception.BackupException;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.ScheduleDto;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LineService;
import de.fhkiel.advancedjava.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class BackupTask {

    @Value("${backup.path}")
    private String EXPORT_FILE_PATH;

    private static final Logger log = LoggerFactory.getLogger(BackupTask.class);
    private ObjectMapper objectMapper;

    private StationService stationService;
    private LineService lineService;
    private DtoConversionService conversionService;

    @Autowired
    public BackupTask(ObjectMapper objectMapper, StationService stationService, LineService lineService, DtoConversionService conversionService){
        this.objectMapper = objectMapper;
        this.stationService = stationService;
        this.lineService = lineService;
        this.conversionService = conversionService;
    }

    @Scheduled(cron = "${backup.cron}")
    private void doBackup(){
        log.info("DOING BACKUP...");

        ScheduleDto scheduleDto = new ScheduleDto();

        // Convert all Stations to StationDTOs and collect them
        ArrayList<StationDto> stationDtos = this.stationService.findAllStationsWithStops()
                .stream()
                .map(station -> this.conversionService.convert(station))
                .collect(Collectors.toCollection(ArrayList::new));

        // Convert all Lines to LineDTOs and collect them
        ArrayList<LineDto> lineDtos = this.lineService.findAllLinesWithLegs().stream()
                .map(line -> this.conversionService.convert(line))
                .collect(Collectors.toCollection(ArrayList::new));

        scheduleDto.setStations(stationDtos);
        scheduleDto.setLines(lineDtos);

        try {
            File target = new File(EXPORT_FILE_PATH);
            if (target.exists())
                target.delete();
            if (target.createNewFile()){
                this.objectMapper.writeValue(target, scheduleDto);
                log.info("Saved backup to " + target.getAbsolutePath());
            }else{
                log.warn("Could not save backup to " + target.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new BackupException("Failed to backup schedule", e.getCause());
        }
    }

}
