package de.fhkiel.advancedjava.io.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LineSerializer extends JsonSerializer<Line> {

    @Override
    public void serialize(Line line, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        // write all the fields and for example compact owner-field

        gen.writeNumberField("lineId", line.getLineId());
        gen.writeStringField("name", line.getName());
        gen.writeStringField("type", line.getType().toString());

        gen.writeArrayFieldStart("sections");
        for (Leg leg : line.getLegs()){
            gen.writeStartObject();
            gen.writeNumberField("beginStopId", leg.getStop().getTransferTo().getToStation().getStationId());
            gen.writeNumberField("endStopId", leg.getConnectingTo().getConnectingToStop().getTransferTo().getToStation().getStationId());
            gen.writeNumberField("durationInMinutes", leg.getConnectingTo().getTime());
            gen.writeNumberField("cost", leg.getCost());
            gen.writeEndObject();

        }
        gen.writeEndArray();

        gen.writeEndObject();
    }

}
