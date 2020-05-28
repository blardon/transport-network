package de.fhkiel.advancedjava.model.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Leg {

    @Id @GeneratedValue
    private Long id;

    @Relationship(type = "CONNECTING_TO", direction = Relationship.OUTGOING)
    private ConnectingTo connectingTo;

    @Relationship(type = "HAS_LEG", direction = Relationship.INCOMING)
    private Stop stop;

    private BigDecimal cost;

    private AccessState state;

    public Long getLegId() {
        return id;
    }

    public ConnectingTo getConnectingTo() {
        return connectingTo;
    }

    public void setConnectingTo(ConnectingTo connectingTo) {
        this.connectingTo = connectingTo;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public AccessState getState() {
        return state;
    }

    public void setState(AccessState state) {
        this.state = state;
    }
}
