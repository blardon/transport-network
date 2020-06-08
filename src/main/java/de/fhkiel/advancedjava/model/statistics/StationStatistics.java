package de.fhkiel.advancedjava.model.statistics;

import de.fhkiel.advancedjava.model.node.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationStatistics {

    private Long id;

    private Station forStation;

    private Long numberOfDisturbancesCreated;

    public Station getForStation() {
        return forStation;
    }

    public void setForStation(Station forStation) {
        this.forStation = forStation;
    }

    public Long getNumberOfDisturbancesCreated() {
        return numberOfDisturbancesCreated;
    }

    public void setNumberOfDisturbancesCreated(Long numberOfDisturbancesCreated) {
        this.numberOfDisturbancesCreated = numberOfDisturbancesCreated;
    }
}
