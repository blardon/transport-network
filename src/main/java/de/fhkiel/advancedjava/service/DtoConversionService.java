package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
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


}
