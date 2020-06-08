package de.fhkiel.advancedjava.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.model.node.dto.StationDto;
import de.fhkiel.advancedjava.model.node.dto.StopDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class VehicleDto {

    @JsonProperty("vehicleId")
    @NotNull
    @Min(0)
    private Long vehicleId;

    @JsonProperty("servesForLineId")
    @NotNull
    private Long servesForLineId;

    @JsonProperty("locatedAtStopId")
    @NotNull
    private StationDto locatedAtStop;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getServesForLineId() {
        return servesForLineId;
    }

    public void setServesForLineId(Long servesForLineId) {
        this.servesForLineId = servesForLineId;
    }

    public StationDto getLocatedAtStop() {
        return locatedAtStop;
    }

    public void setLocatedAtStop(StationDto locatedAtStop) {
        this.locatedAtStop = locatedAtStop;
    }
}
