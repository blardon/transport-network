package de.fhkiel.advancedjava.model;

import de.fhkiel.advancedjava.model.node.Leg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private Long id;

    private Leg forLeg;

    private LocalDateTime dateTime;

    public Leg getForLeg() {
        return forLeg;
    }

    public void setForLeg(Leg forLeg) {
        this.forLeg = forLeg;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
