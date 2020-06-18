package de.fhkiel.advancedjava.model.relationship;

import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Stop;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "CONNECTING_TO")
public class ConnectingTo {

    @Id @GeneratedValue
    private Long id;

    @StartNode
    private Leg connectingWithLeg;

    @EndNode
    private Stop connectingToStop;

    private Long time = 0L;

    public Long getConnectingToId() {
        return id;
    }

    public Leg getConnectingWithLeg() {
        return connectingWithLeg;
    }

    public void setConnectingWithLeg(Leg connectingWithLeg) {
        this.connectingWithLeg = connectingWithLeg;
    }

    public Stop getConnectingToStop() {
        return connectingToStop;
    }

    public void setConnectingToStop(Stop connectingToStop) {
        this.connectingToStop = connectingToStop;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
