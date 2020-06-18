package de.fhkiel.advancedjava.model.schedule.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ScheduleDto {

    @JsonProperty("stations")
    @JsonAlias({"stops"})
    @Valid
    private ArrayList<StationDto> stations;

    @JsonProperty("trafficLines")
    @Valid
    private ArrayList<LineDto> lines;

    public ArrayList<StationDto> getStationDTOs() {
        return stations;
    }

    public ArrayList<LineDto> getLineDTOs() {
        return lines;
    }

    public void setStations(ArrayList<StationDto> stations) {
        this.stations = stations;
    }

    public void setLines(ArrayList<LineDto> lines) {
        this.lines = lines;
    }
}