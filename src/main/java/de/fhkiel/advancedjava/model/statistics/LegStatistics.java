package de.fhkiel.advancedjava.model.statistics;

import de.fhkiel.advancedjava.model.node.Leg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegStatistics {

    private Long id;

    private Leg forLeg;

    private Long numberOfDisturbancesCreated;
    private Long numberOfTicketsSold;

    public Leg getForLeg() {
        return forLeg;
    }

    public void setForLeg(Leg forLeg) {
        this.forLeg = forLeg;
    }

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
}
