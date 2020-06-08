package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.dto.LegDto;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LegToLegDto implements Converter<Leg, LegDto> {

    @Override
    public LegDto convert(Leg leg) {
        LegDto legDto = new LegDto();

        legDto.setCost(leg.getCost());
        legDto.setTime(leg.getConnectingTo().getTime());
        legDto.setBeginStopId(leg.getStop().getTransferTo().getToStation().getStationId());
        legDto.setEndStopId(leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());

        return legDto;
    }

}
