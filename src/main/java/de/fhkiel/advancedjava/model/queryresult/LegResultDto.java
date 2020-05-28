package de.fhkiel.advancedjava.model.queryresult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
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

}
