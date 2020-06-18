package de.fhkiel.advancedjava.model.schedule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.schedule.AccessState;
import de.fhkiel.advancedjava.model.schedule.StopType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class StationDto {

    @JsonProperty("stopId")
    @NotNull
    @Min(0)
    private Long stationId;

    @JsonProperty("types")
    @NotEmpty
    private ArrayList<StopType> types;

    @JsonProperty("state")
    @NotNull
    private AccessState state;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("city")
    @NotBlank
    private String city;

    @JsonProperty("transferTime")
    @Min(0)
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
