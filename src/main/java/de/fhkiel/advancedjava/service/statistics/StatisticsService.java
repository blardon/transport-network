package de.fhkiel.advancedjava.service.statistics;

import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.statistics.LegStatistics;
import de.fhkiel.advancedjava.model.statistics.StationStatistics;
import de.fhkiel.advancedjava.repository.statistics.LegStatisticsRepository;
import de.fhkiel.advancedjava.repository.statistics.StationStatisticsRepository;
import de.fhkiel.advancedjava.service.LegService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class StatisticsService {

    private LegStatisticsRepository legStatisticsRepository;
    private StationStatisticsRepository stationStatisticsRepository;

    private StationService stationService;
    private StopService stopService;
    private LegService legService;

    @Autowired
    public StatisticsService(LegStatisticsRepository legStatisticsRepository, StationStatisticsRepository stationStatisticsRepository, StationService stationService, StopService stopService, LegService legService) {
        this.legStatisticsRepository = legStatisticsRepository;
        this.stationStatisticsRepository = stationStatisticsRepository;
        this.stationService = stationService;
        this.stopService = stopService;
        this.legService = legService;
    }

    public LegStatistics saveLegStatistics(LegStatistics legStatistics){
        return this.legStatisticsRepository.save(Optional.ofNullable(legStatistics).orElseThrow( () -> new WrongInputException("No statistic supplied")));
    }

    public StationStatistics saveStationStatistics(StationStatistics stationStatistics){
        return this.stationStatisticsRepository.save(Optional.ofNullable(stationStatistics).orElseThrow( () -> new WrongInputException("No statistic supplied")));
    }

    public Collection<LegStatistics> findAllLegStatistics(){
        final ArrayList<LegStatistics> result = new ArrayList<>();
        this.legStatisticsRepository.findAll().forEach(result::add);
        return result;
    }

    public Collection<StationStatistics> findAllStationStatistics(){
        final ArrayList<StationStatistics> result = new ArrayList<>();
        this.stationStatisticsRepository.findAll().forEach(result::add);
        return result;
    }

    public LegStatistics createNewStatisticsForLeg(Leg leg){
        LegStatistics legStatistics = new LegStatistics();
        legStatistics.setForLeg(leg);
        legStatistics.setNumberOfDisturbancesCreated(0L);
        legStatistics.setNumberOfTicketsSold(0L);
        return this.saveLegStatistics(legStatistics);
    }

    public StationStatistics createNewStatisticsForStation(Station station){
        StationStatistics stationStatistics = new StationStatistics();
        stationStatistics.setForStation(station);
        stationStatistics.setNumberOfDisturbancesCreated(0L);
        return this.saveStationStatistics(stationStatistics);
    }

    public LegStatistics addTicketBought(Leg leg, Long amount){
        Optional<LegStatistics> optionalLegStatistics = this.legStatisticsRepository.findForLegById(leg.getLegId());

        LegStatistics legStatistics;
        if(optionalLegStatistics.isEmpty()){
            legStatistics = this.createNewStatisticsForLeg(leg);
            legStatistics.setNumberOfTicketsSold(amount);
        }else{
            legStatistics = optionalLegStatistics.get();
            legStatistics.setNumberOfTicketsSold(legStatistics.getNumberOfTicketsSold() + amount);
        }
        return this.saveLegStatistics(legStatistics);
    }

    public LegStatistics addDisturbanceCreated(Leg leg){
        Optional<LegStatistics> optionalLegStatistics = this.legStatisticsRepository.findForLegById(leg.getLegId());

        LegStatistics legStatistics;
        if (optionalLegStatistics.isEmpty()){
            legStatistics = this.createNewStatisticsForLeg(leg);
            legStatistics.setNumberOfDisturbancesCreated(1L);
        }else{
            legStatistics = optionalLegStatistics.get();
            legStatistics.setNumberOfDisturbancesCreated(legStatistics.getNumberOfDisturbancesCreated() + 1L);
        }
        return this.saveLegStatistics(legStatistics);
    }

    public StationStatistics addDisturbanceCreated(Station station){
        Optional<StationStatistics> optionalStationStatistics = this.stationStatisticsRepository.findForStationById(station.getStationId());

        StationStatistics stationStatistics;
        if (optionalStationStatistics.isEmpty()){
            stationStatistics = this.createNewStatisticsForStation(station);
            stationStatistics.setNumberOfDisturbancesCreated(1L);
        }else{
            stationStatistics = optionalStationStatistics.get();
            stationStatistics.setNumberOfDisturbancesCreated(stationStatistics.getNumberOfDisturbancesCreated() + 1L);
        }
        return this.saveStationStatistics(stationStatistics);
    }

    public Long totalNumberOfTicketsBought(){
        return StreamSupport.stream(this.legStatisticsRepository.findAll().spliterator(), false)
                .mapToLong(LegStatistics::getNumberOfTicketsSold).sum();
    }

    public Long totalNumberOfDisturbancesCreated(){
        Long forLegs = StreamSupport.stream(this.legStatisticsRepository.findAll().spliterator(), false)
                .mapToLong(LegStatistics::getNumberOfDisturbancesCreated).sum();
        Long forStations = StreamSupport.stream(this.stationStatisticsRepository.findAll().spliterator(), false)
                .mapToLong(StationStatistics::getNumberOfDisturbancesCreated).sum();
        return forLegs + forStations;
    }

    public Long totalNumberOfStops(){
        return (long) this.stationService.findAllStations().size();
    }

    public Long totalNumberOfStopsByType(StopType type){
        return (long) this.stopService.findStopsByType(type).size();
    }

    public Long totalNumberOfLegs(){
        return (long) this.legService.findAll().size();
    }

}
