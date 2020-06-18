package de.fhkiel.advancedjava.model.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Station {

    @Id
    private Long stationId;

    private String name;

    private String city;

    private AccessState state;

    @Relationship(type = "HAS_STOP", direction = Relationship.OUTGOING)
    private ArrayList<Stop> stops = new ArrayList<>();

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AccessState getState() {
        return state;
    }

    public void setState(AccessState state) {
        this.state = state;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }
}
