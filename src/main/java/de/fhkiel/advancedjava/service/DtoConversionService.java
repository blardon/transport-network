package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.VehicleDto;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.schedule.dto.LegDto;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.model.statistics.LegStatistics;
import de.fhkiel.advancedjava.model.statistics.StationStatistics;
import de.fhkiel.advancedjava.model.statistics.dto.LegStatisticsDto;
import de.fhkiel.advancedjava.model.statistics.dto.StationStatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DtoConversionService {

    private StopService stopService;

    @Autowired
    public DtoConversionService(StopService stopService) {
        this.stopService = stopService;
    }

    public Station convert(StationDto stationDto){
        Station newStation = new Station();

        newStation.setStationId(stationDto.getStationId());
        newStation.setCity(stationDto.getCity());
        newStation.setName(stationDto.getName());
        newStation.setState(stationDto.getState());

        // Create a new stop entity and transfer relationship for every StopType in the station
        stationDto.getTypes().forEach(stopType -> {
            Stop newStop = new Stop();
            TransferTo transferTo = new TransferTo();

            newStop.setType(stopType);

            transferTo.setFromStop(newStop);
            transferTo.setToStation(newStation);
            transferTo.setTime(stationDto.getTransferTime());
            newStop.setTransferTo(transferTo);

            newStation.getStops().add(newStop);
        });
        return newStation;
    }

    public StationDto convert(Station station){
        StationDto stationDto = new StationDto();

        stationDto.setCity(station.getCity());
        stationDto.setName(station.getName());
        stationDto.setState(station.getState());
        stationDto.setStationId(station.getStationId());

        // Set types array with all types that are possible for the stop
        ArrayList<StopType> types = station.getStops().stream()
                .map(Stop::getType).collect(Collectors.toCollection(ArrayList::new));
        stationDto.setTypes(types);

        // Set transfer time if set
        for (Stop stop : station.getStops()){
            if (stop.getTransferTo() != null){
                stationDto.setTransferTime(stop.getTransferTo().getTime());
                break;
            }else{
                stationDto.setTransferTime(0L);
            }
        }

        return stationDto;
    }

    public Line convert(LineDto lineDto){
        Line newLine = new Line();

        newLine.setLineId(lineDto.getLineId());
        newLine.setName(lineDto.getName());
        newLine.setType(lineDto.getType());

        // Create a new leg entity for every section in the line
        lineDto.getLegDTOs().forEach(legDto -> {
            Leg newLeg = new Leg();
            Stop fromStop = this.stopService.findStopByTypeAtStationById(legDto.getBeginStopId(), lineDto.getType());

            newLeg.setState(AccessState.OPENED);
            newLeg.setCost(legDto.getCost());
            newLeg.setStop(fromStop);

            ConnectingTo connectingTo = new ConnectingTo();
            Stop toStop = this.stopService.findStopByTypeAtStationById(legDto.getEndStopId(), lineDto.getType());
            connectingTo.setTime(legDto.getTime());
            connectingTo.setConnectingWithLeg(newLeg);
            connectingTo.setConnectingToStop(toStop);

            newLeg.setConnectingTo(connectingTo);

            newLine.getLegs().add(newLeg);
        });
        return newLine;
    }

    public LineDto convert(Line line){
        LineDto lineDto = new LineDto();

        lineDto.setType(line.getType());
        lineDto.setName(line.getName());
        lineDto.setLineId(line.getLineId());

        // Collect all legDtos for Legs in the Line
        ArrayList<LegDto> legDtos = line.getLegs().stream()
                .map(this::convert)
                .collect(Collectors.toCollection(ArrayList::new));
        lineDto.setLegs(legDtos);

        return lineDto;
    }

    public LegDto convert(Leg leg){
        LegDto legDto = new LegDto();

        legDto.setCost(leg.getCost());
        legDto.setTime(leg.getConnectingTo().getTime());
        legDto.setBeginStopId(leg.getStop().getTransferTo().getToStation().getStationId());
        legDto.setEndStopId(leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());

        return legDto;
    }

    public VehicleDto convert(Vehicle vehicle){
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleId(vehicle.getId());
        vehicleDto.setServesForLineId(vehicle.getServesForLine().getLineId());
        vehicleDto.setLocatedAtStop(this.convert(vehicle.getLocatedAtStop().getTransferTo().getToStation()));
        return vehicleDto;
    }

    public LegStatisticsDto convert(LegStatistics legStatistics){
        LegStatisticsDto legStatisticsDto = new LegStatisticsDto();
        legStatisticsDto.setNumberOfDisturbancesCreated(legStatistics.getNumberOfDisturbancesCreated());
        legStatisticsDto.setNumberOfTicketsSold(legStatistics.getNumberOfTicketsSold());
        legStatisticsDto.setLeg(this.convert(legStatistics.getForLeg()));
        return legStatisticsDto;
    }

    public StationStatisticsDto convert(StationStatistics stationStatistics){
        StationStatisticsDto stationStatisticsDto = new StationStatisticsDto();
        stationStatisticsDto.setNumberOfDisturbancesCreated(stationStatistics.getNumberOfDisturbancesCreated());
        stationStatisticsDto.setForStop(this.convert(stationStatistics.getForStation()));
        return stationStatisticsDto;
    }

}
