package de.fhkiel.advancedjava.model.queryresult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ConnectionResultDto {

    @JsonProperty("stations")
    private ArrayList<StationDto> stationDtos;

    @JsonProperty("lines")
    private ArrayList<LineResultDto> lineResultDtos;

    public ArrayList<StationDto> getStationDtos() {
        return stationDtos;
    }

    public void setStationDtos(ArrayList<StationDto> stationDtos) {
        this.stationDtos = stationDtos;
    }
}
