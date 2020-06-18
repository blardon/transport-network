package de.fhkiel.advancedjava.io.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class StationSerializer extends JsonSerializer<Station> {

    @Override
    public void serialize(Station station, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        // write all the fields and for example compact owner-field

        gen.writeNumberField("stopId", station.getStationId());
        gen.writeStringField("name", station.getName());
        gen.writeStringField("city", station.getCity());
        gen.writeStringField("state", station.getState().toString());

        gen.writeArrayFieldStart("types");
        for (Stop stop : station.getStops()){
            gen.writeString(stop.getType().toString());
        }
        gen.writeEndArray();

        for (Stop stop : station.getStops()){
            if (stop.getTransferTo() != null){
                gen.writeNumberField("transferTime", stop.getTransferTo().getTime());
                break;
            }else{
                gen.writeNumberField("transferTime", 0);
            }
        }
        gen.writeEndObject();
    }

}
