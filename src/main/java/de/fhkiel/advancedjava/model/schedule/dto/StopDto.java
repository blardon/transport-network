package de.fhkiel.advancedjava.model.schedule.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.StopType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class StopDto {

    @JsonProperty("type")
    private StopType type;

    @JsonProperty("state")
    private AccessState state;

    @JsonProperty("name")
    private String name;

    @JsonProperty("city")
    private String city;

    public StopType getType() {
        return type;
    }

    public void setType(StopType type) {
        this.type = type;
    }

    public AccessState getState() {
        return state;
    }

    public void setState(AccessState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
