package de.fhkiel.advancedjava.model.schedule.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

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
    private List<StationDto> stations;

    @JsonProperty("trafficLines")
    @Valid
    private List<LineDto> lines;

    public List<StationDto> getStationDTOs() {
        return stations;
    }

    public List<LineDto> getLineDTOs() {
        return lines;
    }

    public void setStations(List<StationDto> stations) {
        this.stations = stations;
    }

    public void setLines(List<LineDto> lines) {
        this.lines = lines;
    }
}
