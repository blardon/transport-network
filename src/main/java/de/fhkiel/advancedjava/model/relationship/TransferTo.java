package de.fhkiel.advancedjava.model.relationship;

import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import lombok.*;
import org.neo4j.ogm.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@RelationshipEntity(type = "TRANSFER_TO")
public class TransferTo {

    @Id @GeneratedValue
    private Long id;

    @StartNode
    private Stop fromStop;

    @EndNode
    private Station toStation;

    private Long time = 0L;

    public Long getTransferToId() {
        return id;
    }

    public Stop getFromStop() {
        return fromStop;
    }

    public void setFromStop(Stop fromStop) {
        this.fromStop = fromStop;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
