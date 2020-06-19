package de.fhkiel.advancedjava.model.queryresult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ConnectionResultDto {

    @JsonProperty("totalCost")
    private BigDecimal totalCost;

    @JsonProperty("totalTime")
    private Long totalTime;

    @JsonProperty("stations")
    private List<StationDto> stationDtos;

    @JsonProperty("lines")
    private List<LineDto> lineDtos;

    public List<StationDto> getStationDtos() {
        return stationDtos;
    }

    public void setStationDtos(List<StationDto> stationDtos) {
        this.stationDtos = stationDtos;
    }

    public List<LineDto> getLineDtos() {
        return lineDtos;
    }

    public void setLineDtos(List<LineDto> lineDtos) {
        this.lineDtos = lineDtos;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
}
