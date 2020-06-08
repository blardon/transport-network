package de.fhkiel.advancedjava.model.statistics.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class StationStatisticsDto {

    @JsonProperty("numberOfDisturbancesCreated")
    private Long numberOfDisturbancesCreated;

    @JsonProperty("forStop")
    private StationDto forStop;

    public Long getNumberOfDisturbancesCreated() {
        return numberOfDisturbancesCreated;
    }

    public void setNumberOfDisturbancesCreated(Long numberOfDisturbancesCreated) {
        this.numberOfDisturbancesCreated = numberOfDisturbancesCreated;
    }

    public StationDto getForStop() {
        return forStop;
    }

    public void setForStop(StationDto forStop) {
        this.forStop = forStop;
    }
}
