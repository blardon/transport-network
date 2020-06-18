package de.fhkiel.advancedjava.model.queryresult;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fhkiel.advancedjava.model.schedule.StopType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LineResultDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private StopType type;

    @JsonProperty("sections")
    private ArrayList<LegResultDto> legResultDtos;

}
