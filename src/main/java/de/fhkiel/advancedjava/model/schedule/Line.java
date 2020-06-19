package de.fhkiel.advancedjava.model.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Line {

    @Id
    private Long lineId;

    private String name;

    private StopType type;

    @Relationship(type = "CARRIES_OUT", direction = Relationship.OUTGOING)
    private List<Leg> legs = new ArrayList<>();

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StopType getType() {
        return type;
    }

    public void setType(StopType type) {
        this.type = type;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
