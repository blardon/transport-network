package de.fhkiel.advancedjava.model.node.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.StopType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LineDto {

    @JsonProperty("lineId")
    private Long lineId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private StopType type;

    @JsonProperty("sections")
    private ArrayList<LegDto> legs;

    public Long getLineId() {
        return lineId;
    }

    public String getName() {
        return name;
    }

    public StopType getType() {
        return type;
    }

    public ArrayList<LegDto> getLegDTOs() {
        return legs;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(StopType type) {
        this.type = type;
    }

    public void setLegs(ArrayList<LegDto> legs) {
        this.legs = legs;
    }
}