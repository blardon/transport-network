package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResultDto;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.VehicleDto;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.schedule.dto.LegDto;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.ScheduleDto;
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
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * DtoConversionService is responsible to convert domain models into DTO representation
 *
 * @author Bennet v. Lardon
 */
@Service
public class DtoConversionService {

    private StopService stopService;

    @Autowired
    public DtoConversionService(StopService stopService) {
        this.stopService = stopService;
    }

    /**
     * <p>Converts stations and lines into a ScheduleDto
     * </p>
     *
     * @param allStations the stations to be included in the output
     * @param allLines    the lines to be included in the output
     * @return the converted ScheduleDto
     */
    public ScheduleDto convert(Collection<Station> allStations, Collection<Line> allLines) {
        ScheduleDto scheduleDto = new ScheduleDto();

        // Convert all Stations to StationDTOs and collect them
        ArrayList<StationDto> stationDtos = allStations
                .stream()
                .map(this::convert)
                .collect(Collectors.toCollection(ArrayList::new));

        // Convert all Lines to LineDTOs and collect them
        ArrayList<LineDto> lineDtos = allLines
                .stream()
                .map(this::convert)
                .collect(Collectors.toCollection(ArrayList::new));

        scheduleDto.setStations(stationDtos);
        scheduleDto.setLines(lineDtos);

        return scheduleDto;
    }

    /**
     * <p>Converts a StationDto into a Station
     * </p>
     *
     * @param stationDto the StationDto to be converted to a Station
     * @return the converted StationDto
     */
    public Station convert(StationDto stationDto) {
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

    /**
     * <p>Converts a Station into a StationDto
     * </p>
     *
     * @param station the Station to be converted to a StationDto
     * @return the converted StationDto
     */
    public StationDto convert(Station station) {
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
        for (Stop stop : station.getStops()) {
            if (stop.getTransferTo() != null) {
                stationDto.setTransferTime(stop.getTransferTo().getTime());
                break;
            }
        }

        return stationDto;
    }

    /**
     * <p>Converts a LineDto into a Line
     * </p>
     *
     * @param lineDto the LineDto to be converted to a Line
     * @return the converted Line
     */
    public Line convert(LineDto lineDto) {
        Line newLine = new Line();

        newLine.setLineId(lineDto.getLineId());
        newLine.setName(lineDto.getName());
        newLine.setType(lineDto.getType());

        // Create a new leg entity for every section in the line
        lineDto.getLegDTOs().forEach(legDto -> {
            Leg newLeg = new Leg();
            Stop fromStop = this.stopService.findStopByTypeAtStationById(legDto.getBeginStopId(), lineDto.getType());

            newLeg.setState(legDto.getState());
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

    /**
     * <p>Converts a Line into a LineDto
     * </p>
     *
     * @param line the Line to be converted to a LineDto
     * @return the converted LineDto
     */
    public LineDto convert(Line line) {
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

    /**
     * <p>Converts a Leg into a LegDto
     * </p>
     *
     * @param leg the leg to be converted to a LegDto
     * @return the converted LegDto
     */
    public LegDto convert(Leg leg) {
        LegDto legDto = new LegDto();

        legDto.setLegId(leg.getLegId());
        legDto.setState(leg.getState());
        legDto.setCost(leg.getCost());
        legDto.setTime(leg.getConnectingTo().getTime());
        legDto.setBeginStopId(leg.getStop().getTransferTo().getToStation().getStationId());
        legDto.setEndStopId(leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());

        return legDto;
    }

    /**
     * <p>Converts a model Vehicle into the VehicleDto presentation
     * </p>
     *
     * @param vehicle The vehicle model to convert into a VehicleDto
     * @return the converted Vehicle
     */
    public VehicleDto convert(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVehicleId(vehicle.getId());
        vehicleDto.setServesForLineId(vehicle.getServesForLine().getLineId());
        vehicleDto.setLocatedAtStop(this.convert(vehicle.getLocatedAtStop().getTransferTo().getToStation()));
        return vehicleDto;
    }

    public LegStatisticsDto convert(LegStatistics legStatistics) {
        LegStatisticsDto legStatisticsDto = new LegStatisticsDto();
        legStatisticsDto.setNumberOfDisturbancesCreated(legStatistics.getNumberOfDisturbancesCreated());
        legStatisticsDto.setNumberOfTicketsSold(legStatistics.getNumberOfTicketsSold());
        legStatisticsDto.setLeg(this.convert(legStatistics.getForLeg()));
        return legStatisticsDto;
    }

    public StationStatisticsDto convert(StationStatistics stationStatistics) {
        StationStatisticsDto stationStatisticsDto = new StationStatisticsDto();
        stationStatisticsDto.setNumberOfDisturbancesCreated(stationStatistics.getNumberOfDisturbancesCreated());
        stationStatisticsDto.setForStop(this.convert(stationStatistics.getForStation()));
        return stationStatisticsDto;
    }

    /**
     * <p>Converts a ConnectionResult into a ConnectionResultDto representation
     * </p>
     *
     * @param connectionResult The ConnectionResult to be converted into a ConnectionResultDto
     * @return the converted ConnectionResultDto
     */
    public ConnectionResultDto convertResult(ConnectionResult connectionResult) {
        ConnectionResultDto connectionResultDto = new ConnectionResultDto();

        connectionResultDto.setLineDtos(connectionResult.getLines().stream()
                .map(this::convert)
                .collect(Collectors.toCollection(ArrayList::new)));

        connectionResultDto.setStationDtos(connectionResult.getStations().stream()
                .map(this::convert)
                .collect(Collectors.toCollection(ArrayList::new)));

        connectionResultDto.setTotalCost(connectionResult.getTotalCost());
        connectionResultDto.setTotalTime(connectionResult.getTotalTime());

        return connectionResultDto;
    }

}
