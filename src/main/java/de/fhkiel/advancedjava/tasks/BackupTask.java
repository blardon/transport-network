package de.fhkiel.advancedjava.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhkiel.advancedjava.exception.BackupException;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
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
import java.util.Collection;
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

        Collection<Station> allStations = this.stationService.findAllStationsWithStops();
        Collection<Line> allLines = this.lineService.findAllLinesWithLegs();

        ScheduleDto scheduleDto = this.conversionService.convert(allStations, allLines);

        try {
            File target = new File(EXPORT_FILE_PATH);
            if (target.exists())
                java.nio.file.Files.delete(target.toPath());
            if (target.createNewFile()){
                this.objectMapper.writeValue(target, scheduleDto);
                log.info(String.format("Saved backup to %s", target.getAbsolutePath()));
            }else{
                log.warn(String.format("Could not save backup to %s", target.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new BackupException("Failed to backup schedule", e.getCause());
        }
    }

}
