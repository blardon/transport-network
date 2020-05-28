package de.fhkiel.advancedjava.model.node;

import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;

@NodeEntity
public class Stop {

    @Id
    private Long stopId;

    private StopType type;

    @Relationship(type = "HAS_LEG", direction = Relationship.OUTGOING)
    private HashSet<Leg> legs;

    @Relationship(type = "TRANSFER_TO", direction = Relationship.OUTGOING)
    private TransferTo transferTo;

}
