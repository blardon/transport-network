package de.fhkiel.advancedjava.model.queryresult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.schedule.dto.StationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LegResultDto {

    @JsonProperty("fromStation")
    private StationDto fromStationDto;

    @JsonProperty("toStation")
    private StationDto toStationDto;

    @JsonProperty("durationInMinutes")
    private Long time;

    @JsonProperty("cost")
    private BigDecimal cost;

    public StationDto getFromStationDto() {
        return fromStationDto;
    }

    public void setFromStationDto(StationDto fromStationDto) {
        this.fromStationDto = fromStationDto;
    }

    public StationDto getToStationDto() {
        return toStationDto;
    }

    public void setToStationDto(StationDto toStationDto) {
        this.toStationDto = toStationDto;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
