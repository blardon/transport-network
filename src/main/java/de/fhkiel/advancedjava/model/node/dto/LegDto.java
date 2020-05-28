package de.fhkiel.advancedjava.model.node.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LegDto {

    @JsonProperty("beginStopId")
    private Long beginStopId;

    @JsonProperty("endStopId")
    private Long endStopId;

    @JsonProperty("durationInMinutes")
    private Long time;

    @JsonProperty("cost")
    private BigDecimal cost = BigDecimal.valueOf(0.00);

    public Long getBeginStopId() {
        return beginStopId;
    }

    public Long getEndStopId() {
        return endStopId;
    }

    public Long getTime() {
        return time;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setBeginStopId(Long beginStopId) {
        this.beginStopId = beginStopId;
    }

    public void setEndStopId(Long endStopId) {
        this.endStopId = endStopId;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
