package de.fhkiel.advancedjava.model.relationship;

import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "TRANSFER_TO")
public class TransferTo {

    @Id @GeneratedValue
    private Long transferToId;

    @StartNode
    private Stop fromStop;

    @EndNode
    private Station toStation;

    private Long time;

}
