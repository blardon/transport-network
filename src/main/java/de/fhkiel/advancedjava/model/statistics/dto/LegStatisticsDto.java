package de.fhkiel.advancedjava.model.statistics.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.node.dto.LegDto;
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
public class LegStatisticsDto {

    @JsonProperty("numberOfDisturbancesCreated")
    private Long numberOfDisturbancesCreated;

    @JsonProperty("numberOfTicketsSold")
    private Long numberOfTicketsSold;

    @JsonProperty("connection")
    private LegDto leg;

    public Long getNumberOfDisturbancesCreated() {
        return numberOfDisturbancesCreated;
    }

    public void setNumberOfDisturbancesCreated(Long numberOfDisturbancesCreated) {
        this.numberOfDisturbancesCreated = numberOfDisturbancesCreated;
    }

    public Long getNumberOfTicketsSold() {
        return numberOfTicketsSold;
    }

    public void setNumberOfTicketsSold(Long numberOfTicketsSold) {
        this.numberOfTicketsSold = numberOfTicketsSold;
    }

    public LegDto getLeg() {
        return leg;
    }

    public void setLeg(LegDto leg) {
        this.leg = leg;
    }
}
