package de.fhkiel.advancedjava.model.node.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class StationDto {

    @JsonProperty("stopId")
    private Long stationId;

    @JsonProperty("types")
    private ArrayList<StopType> types;

    @JsonProperty("state")
    private AccessState state;

    @JsonProperty("name")
    private String name;

    @JsonProperty("city")
    private String city;

    @JsonProperty("transferTime")
    private Long transferTime = 0L;

    public Long getStationId() {
        return stationId;
    }

    public ArrayList<StopType> getTypes() {
        return types;
    }

    public AccessState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Long getTransferTime() {
        return transferTime;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public void setTypes(ArrayList<StopType> types) {
        this.types = types;
    }

    public void setState(AccessState state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTransferTime(Long transferTime) {
        this.transferTime = transferTime;
    }
}
