package de.fhkiel.advancedjava.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@JsonComponent
public class StationDeserializer extends JsonDeserializer<Station> {
    @Override
    public Station deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Long id = node.get("stopId").asLong();
        AccessState state = AccessState.valueOf(node.get("state").asText());
        String name = node.get("name").asText();
        String city = node.get("city").asText();
        Long time = node.get("transferTime").asLong();

        Station station = new Station();
        station.setState(state);
        station.setStationId(id);
        station.setName(name);
        station.setCity(city);

        ArrayList<Stop> stops = StreamSupport.stream(node.get("types").spliterator(), false)
                .map(jsonNode -> {
                    Stop stop = new Stop();
                    stop.setType(StopType.valueOf(jsonNode.asText()));

                    TransferTo transferTo = new TransferTo();
                    transferTo.setTime(time);
                    transferTo.setFromStop(stop);
                    transferTo.setToStation(station);
                    stop.setTransferTo(transferTo);

                    return stop;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        station.setStops(stops);
        return station;
    }
}
