package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.dto.LegDto;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class LineToLineDto implements Converter<Line, LineDto> {
    @Override
    public LineDto convert(Line line) {
        LineDto lineDto = new LineDto();

        lineDto.setType(line.getType());
        lineDto.setName(line.getName());
        lineDto.setLineId(line.getLineId());

        // Collect all legDtos for Legs in the Line
        ArrayList<LegDto> legDtos = line.getLegs().stream().map(leg -> {
            LegDto legDto = new LegDto();
            legDto.setCost(leg.getCost());
            legDto.setTime(leg.getConnectingTo().getTime());
            legDto.setBeginStopId(leg.getStop().getTransferTo().getToStation().getStationId());
            legDto.setEndStopId(leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());
            return legDto;
        }).collect(Collectors.toCollection(ArrayList::new));
        lineDto.setLegs(legDtos);

        return lineDto;
    }
}
