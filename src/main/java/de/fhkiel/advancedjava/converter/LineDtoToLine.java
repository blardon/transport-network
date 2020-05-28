package de.fhkiel.advancedjava.converter;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LineDtoToLine implements Converter<LineDto, Line> {

    private StopService stopService;

    @Autowired
    public LineDtoToLine(StopService stopService){
        this.stopService = stopService;
    }

    @Override
    public Line convert(LineDto lineDto) {
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
}
