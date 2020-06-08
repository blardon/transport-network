package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.Vehicle;
import de.fhkiel.advancedjava.model.VehicleDto;
import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.LegDto;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.statistics.LegStatistics;
import de.fhkiel.advancedjava.model.statistics.StationStatistics;
import de.fhkiel.advancedjava.model.statistics.dto.LegStatisticsDto;
import de.fhkiel.advancedjava.model.statistics.dto.StationStatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Service;

@Service
public class DtoConversionService {

    private GenericConversionService genericConversionService;

    @Autowired
    public void setGenericConversionService(GenericConversionService genericConversionService){
        this.genericConversionService = genericConversionService;
    }

    public Station convert(StationDto stationDto){
        if (this.genericConversionService.canConvert(StationDto.class, Station.class)){
            return this.genericConversionService.convert(stationDto, Station.class);
        }
        throw new RuntimeException("Wrong input");
    }

    public StationDto convert(Station station){
        if (this.genericConversionService.canConvert(Station.class, StationDto.class)){
            return this.genericConversionService.convert(station, StationDto.class);
        }
        throw new RuntimeException("Wrong input");
    }

    public Line convert(LineDto lineDto){
        if (this.genericConversionService.canConvert(LineDto.class, Line.class)){
            return this.genericConversionService.convert(lineDto, Line.class);
        }
        throw new RuntimeException("Wrong input");
    }

    public LineDto convert(Line line){
        if (this.genericConversionService.canConvert(Line.class, LineDto.class)){
            return this.genericConversionService.convert(line, LineDto.class);
        }
        throw new RuntimeException("Wrong input");
    }

    public LegDto convert(Leg leg){
        if (this.genericConversionService.canConvert(Leg.class, LegDto.class)){
            return this.genericConversionService.convert(leg, LegDto.class);
        }
        throw new RuntimeException("Wrong input");
    }

    public VehicleDto convert(Vehicle vehicle){
        if (this.genericConversionService.canConvert(Vehicle.class, VehicleDto.class)){
            VehicleDto vehicleDto = this.genericConversionService.convert(vehicle, VehicleDto.class);
            vehicleDto.setLocatedAtStop(this.convert(vehicle.getLocatedAtStop().getTransferTo().getToStation()));
            return vehicleDto;
        }
        throw new RuntimeException("Wrong input");
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
