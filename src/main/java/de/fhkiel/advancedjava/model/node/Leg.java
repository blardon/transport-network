package de.fhkiel.advancedjava.model.node;

import de.fhkiel.advancedjava.model.AccessState;
import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.math.BigDecimal;

@NodeEntity
public class Leg {

    @Id @GeneratedValue
    private Long legId;

    @Relationship(type = "CONNECTING_TO", direction = Relationship.OUTGOING)
    private ConnectingTo connectingTo;

    @Relationship(type = "CARRIES_OUT", direction = Relationship.INCOMING)
    private Line line;

    private BigDecimal cost;

    private AccessState state;

}
