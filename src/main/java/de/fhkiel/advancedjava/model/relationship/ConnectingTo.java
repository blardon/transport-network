package de.fhkiel.advancedjava.model.relationship;

import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Stop;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CONNECTING_TO")
public class ConnectingTo {

    @Id @GeneratedValue
    private Long connectingToId;

    @StartNode
    private Leg connectingWithLeg;

    @EndNode
    private Stop connectingToStop;

    private Long time;

}
