package de.fhkiel.advancedjava.io.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;

@JsonComponent
public class StationDeserializer extends JsonDeserializer<Station> {

    @Override
    public Station deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Station station = new Station();

        // all mandatory fields for a station
        Long id = null;
        String name = null;
        String city = null;
        AccessState accessState = null;
        ArrayList<Stop> stops = new ArrayList<>();

        JsonNode rootNode = p.getCodec().readTree(p);
        id = rootNode.get("stopId").longValue();
        name = rootNode.get("name").textValue();
        city = rootNode.get("city").textValue();
        accessState = AccessState.valueOf(rootNode.get("state").textValue());

        rootNode.get("types").elements().forEachRemaining(jsonNode -> {
            StopType type = StopType.valueOf(jsonNode.asText());

            Stop stop = new Stop();

            TransferTo transferTo = new TransferTo();
            transferTo.setTime(rootNode.get("transferTime").asLong());
            transferTo.setFromStop(stop);
            transferTo.setToStation(station);

            stop.setTransferTo(transferTo);
            stop.setType(type);
            stops.add(stop);
        });

        station.setStationId(id);
        station.setName(name);
        station.setCity(city);
        station.setState(accessState);
        station.setStops(stops);

        return station;
    }

}
