package de.fhkiel.advancedjava.io.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

@JsonComponent
public class LineDeserializer extends JsonDeserializer<Line> {

    private StopService stopService;

    @Autowired
    public LineDeserializer(StopService stopService) {
        this.stopService = stopService;
    }

    @Override
    public Line deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Line line = new Line();

        // all mandatory fields for a line
        Long id = null;
        String name = null;
        StopType type = null;
        ArrayList<Leg> legs = new ArrayList<>();

        JsonNode rootNode = p.getCodec().readTree(p);
        id = rootNode.get("lineId").longValue();
        name = rootNode.get("name").textValue();
        type = StopType.valueOf(rootNode.get("type").textValue());

        for (Iterator<JsonNode> it = rootNode.get("sections").elements(); it.hasNext(); ) {
            JsonNode jsonNode = it.next();

            Long beginStopId = jsonNode.get("beginStopId").longValue();
            Long endStopId = jsonNode.get("endStopId").longValue();
            Long durationInMinutes = jsonNode.get("durationInMinutes").asLong();
            BigDecimal cost = jsonNode.get("cost").decimalValue();

            Stop beginStop = this.stopService.findStopByTypeAtStationById(beginStopId, type);
            Stop endStop = this.stopService.findStopByTypeAtStationById(endStopId, type);

            Leg leg = new Leg();
            leg.setCost(cost);
            leg.setState(AccessState.OPENED);
            leg.setStop(beginStop);

            ConnectingTo connectingTo = new ConnectingTo();
            connectingTo.setTime(durationInMinutes);
            connectingTo.setConnectingWithLeg(leg);
            connectingTo.setConnectingToStop(endStop);

            leg.setConnectingTo(connectingTo);
            legs.add(leg);
        }

        line.setLineId(id);
        line.setName(name);
        line.setType(type);
        line.setLegs(legs);

        return line;
    }

}
