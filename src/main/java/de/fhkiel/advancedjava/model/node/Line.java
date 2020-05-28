package de.fhkiel.advancedjava.model.node;

import de.fhkiel.advancedjava.model.StopType;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Line {

    @Id
    private Long lineId;

    private String name;

    private StopType type;

}
