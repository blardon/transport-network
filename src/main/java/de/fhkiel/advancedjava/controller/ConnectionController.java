package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.Ticket;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.TicketService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    private StationService stationService;
    private LegService legService;
    private TicketService ticketService;
    private StatisticsService statisticsService;
    private DtoConversionService conversionService;

    @Autowired
    public ConnectionController(StationService stationService, LegService legService, TicketService ticketService, StatisticsService statisticsService, DtoConversionService conversionService){
        this.stationService = stationService;
        this.legService = legService;
        this.ticketService = ticketService;
        this.statisticsService = statisticsService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/from/{stationNameFrom}/to/{stationNameTo}")
    public ResponseEntity<Object> getFastestConnectionWithoutTransferTime
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        Iterable<Map<String, Object>> result = this.stationService.findFastestPathWithoutTransferTime(stationNameFrom, stationNameTo);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/from/{stationNameFrom}/to/{stationNameTo}/withTransferTime")
    public ResponseEntity<ConnectionResult> getFastestConnectionWithTransferTime
            (@PathVariable String stationNameFrom, @PathVariable String stationNameTo){
        ConnectionResult result = this.stationService.findFastestPathWithTransferTime(stationNameFrom, stationNameTo);

        //ConnectionResultDto connectionResultDto = new ConnectionResultDto();
        //connectionResultDto.setStationDtos(result.getStations()
        //        .stream()
        //        .map(station -> conversionService.convert(station, StationDto.class))
        //        .collect(Collectors.toCollection(ArrayList::new)));

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "buytickets/{amount}/from/{stationNameFrom}/to/{stationNameTo}/with/{type}")
    public ResponseEntity<Object> buyTicketsForLeg(@PathVariable Long amount, @PathVariable String stationNameFrom, @PathVariable String stationNameTo, @PathVariable StopType type){
        Leg leg = this.legService.findLegByTypeBetweenStations(type, stationNameFrom, stationNameTo);

        if (amount < 1 || amount > 20){
            throw new WrongInputException("Amount must be at least 1 and max. 20");
        }

        for (int i = 0; i < amount; i++){
            Ticket ticket = new Ticket();
            ticket.setDateTime(LocalDateTime.now());
            ticket.setForLeg(leg);
            this.ticketService.saveTicket(ticket);
        }

        this.statisticsService.addTicketBought(leg, amount);

        BigDecimal price = leg.getCost().multiply(BigDecimal.valueOf(amount));
        return ResponseEntity.ok(
                String.format("You have successfully bought %d ticket(s) from %s to %s for %,.2f€.",
                        amount, stationNameFrom, stationNameTo, price.setScale(2, RoundingMode.DOWN)));
    }

}
