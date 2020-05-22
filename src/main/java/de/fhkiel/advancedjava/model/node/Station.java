package de.fhkiel.advancedjava.model.node;

import de.fhkiel.advancedjava.model.AccessState;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;

@NodeEntity
public class Station {

    @Id @GeneratedValue
    private Long stationId;

    private String name;

    private String city;

    private AccessState state;

    @Relationship(type = "HAS_STOP", direction = Relationship.OUTGOING)
    private HashSet<Stop> stops;

}
