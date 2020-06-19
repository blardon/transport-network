package de.fhkiel.advancedjava.model;

import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Stop;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "SERVES_FOR", direction = Relationship.OUTGOING)
    private Line servesForLine;

    @Relationship(type = "LOCATED_AT")
    private Stop locatedAtStop;

    public Long getId() {
        return id;
    }

    public Line getServesForLine() {
        return servesForLine;
    }

    public void setServesForLine(Line servesForLine) {
        this.servesForLine = servesForLine;
    }

    public Stop getLocatedAtStop() {
        return locatedAtStop;
    }

    public void setLocatedAtStop(Stop locatedAtStop) {
        this.locatedAtStop = locatedAtStop;
    }
}
