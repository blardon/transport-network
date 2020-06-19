package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResultDto;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    private StationService stationService;
    private LegService legService;
    private StatisticsService statisticsService;
    private DtoConversionService conversionService;

    @Autowired
    public ConnectionController(StationService stationService, LegService legService, StatisticsService statisticsService, DtoConversionService conversionService){
        this.stationService = stationService;
        this.legService = legService;
        this.statisticsService = statisticsService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/from/{stationNameFrom}/to/{stationNameTo}")
    public ResponseEntity<ConnectionResultDto> getFastestConnectionWithTransferTime
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        ConnectionResult connectionResult = this.stationService.findFastestPathWithTransferTime(stationNameFrom, stationNameTo);

        ConnectionResultDto result = this.conversionService.convertResult(connectionResult);

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/stops/{nStops}/minutes/{mMinutes}")
    public ResponseEntity<ConnectionResultDto> getNStopsInMMinutes
            (@PathVariable Long nStops, @PathVariable Long mMinutes){
        ConnectionResult connectionResult = this.stationService.findNStopsInMMinutes(nStops, mMinutes);

        ConnectionResultDto result = this.conversionService.convertResult(connectionResult);

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/cheapest/from/{stationNameFrom}/to/{stationNameTo}")
    public ResponseEntity<Collection<ConnectionResultDto>> find3CheapestConnections
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        Collection<ConnectionResult> connectionResults = this.stationService.find3Cheapest(stationNameFrom, stationNameTo);

        Collection<ConnectionResultDto> results = connectionResults.stream()
                .map(result -> this.conversionService.convertResult(result))
                .collect(Collectors.toCollection(ArrayList::new));

        return ResponseEntity.ok(results);
    }

    @GetMapping(path = "buytickets/{amount}/from/{stationNameFrom}/to/{stationNameTo}/with/{type}")
    public ResponseEntity<Object> buyTicketsForLeg(@PathVariable Long amount, @PathVariable String stationNameFrom, @PathVariable String stationNameTo, @PathVariable StopType type){
        Leg leg = this.legService.findLegByTypeBetweenStations(type, stationNameFrom, stationNameTo);

        if (amount < 1 || amount > 20){
            throw new WrongInputException("Amount must be at least 1 and max. 20");
        }

        this.statisticsService.addTicketBought(leg, amount);

        BigDecimal price = leg.getCost().multiply(BigDecimal.valueOf(amount));
        return ResponseEntity.ok(
                String.format("You have successfully bought %d ticket(s) from %s to %s for %,.2f€.",
                        amount, stationNameFrom, stationNameTo, price.setScale(2, RoundingMode.DOWN)));
    }

}
