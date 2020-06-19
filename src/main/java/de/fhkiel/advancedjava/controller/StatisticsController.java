package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.statistics.dto.LegStatisticsDto;
import de.fhkiel.advancedjava.model.statistics.dto.StationStatisticsDto;
import de.fhkiel.advancedjava.model.statistics.dto.StatisticsResultDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * StatisticsController manages statistics.
 *
 * @author Bennet v. Lardon
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;
    private DtoConversionService dtoConversionService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, DtoConversionService dtoConversionService) {
        this.statisticsService = statisticsService;
        this.dtoConversionService = dtoConversionService;
    }

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticsResultDto> getStatistics() {
        StatisticsResultDto result = new StatisticsResultDto();

        result.setTotalNumberOfStops(this.statisticsService.totalNumberOfStops());
        result.setTotalNumberOfBusStops(this.statisticsService.totalNumberOfStopsByType(StopType.BUS));
        result.setTotalNumberOfSubwayStops(this.statisticsService.totalNumberOfStopsByType(StopType.SUBWAY));
        result.setTotalNumberOfSuburbanTrainStops(this.statisticsService.totalNumberOfStopsByType(StopType.SUBURBAN_TRAIN));

        result.setTotalNumberOfConnections(this.statisticsService.totalNumberOfLegs());

        result.setTotalNumberOfTicketsBought(this.statisticsService.totalNumberOfTicketsBought());
        result.setTotalNumberOfDisturbancesCreated(this.statisticsService.totalNumberOfDisturbancesCreated());

        ArrayList<LegStatisticsDto> legStatisticsDtos = this.statisticsService.findAllLegStatistics()
                .stream()
                .map(legStatistics -> this.dtoConversionService.convert(legStatistics)).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<StationStatisticsDto> stationStatisticsDto = this.statisticsService.findAllStationStatistics()
                .stream()
                .map(stationStatistics -> this.dtoConversionService.convert(stationStatistics)).collect(Collectors.toCollection(ArrayList::new));

        result.setPerConnectionList(legStatisticsDtos);
        result.setPerStopList(stationStatisticsDto);

        return ResponseEntity.ok(result);
    }

}
