package de.fhkiel.advancedjava.model;

import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;

import java.util.Set;

public class Schedule {

    private Set<Station> stops;

    private Set<Line> trafficLines;

    public Schedule(Set<Station> stops, Set<Line> trafficLines) {
        this.stops = stops;
        this.trafficLines = trafficLines;
    }

    public Set<Station> getStops() {
        return stops;
    }

    public void setStops(Set<Station> stops) {
        this.stops = stops;
    }

    public Set<Line> getTrafficLines() {
        return trafficLines;
    }

    public void setTrafficLines(Set<Line> trafficLines) {
        this.trafficLines = trafficLines;
    }
}
