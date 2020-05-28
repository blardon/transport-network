package de.fhkiel.advancedjava.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.fhkiel.advancedjava.model.Schedule;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.service.LineService;
import de.fhkiel.advancedjava.service.StationService;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ScheduleDeserializer extends JsonDeserializer<Schedule> {

    private StationService stationService;
    private StopService stopService;
    private LineService lineService;

    @Autowired
    public ScheduleDeserializer(StationService stationService, StopService stopService, LineService lineService) {
        this.stationService = stationService;
        this.stopService = stopService;
        this.lineService = lineService;
    }

    @Override
    public Schedule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode node = objectCodec.readTree(p);

        ArrayList<Station> stations = StreamSupport.stream(node.get("stops").spliterator(), false)
                .map(jsonNode -> {
                    try {
                        return objectCodec.treeToValue(jsonNode, Station.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        return null;
    }
}
