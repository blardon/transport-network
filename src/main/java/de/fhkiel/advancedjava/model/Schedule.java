package de.fhkiel.advancedjava.model;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;

import javax.validation.Valid;
import java.util.LinkedHashSet;

public class Schedule {

    @Valid
    private LinkedHashSet<Station> stops;

    private LinkedHashSet<Line> trafficLines;

    public Schedule(LinkedHashSet<Station> stops, LinkedHashSet<Line> trafficLines) {
        this.stops = stops;
        this.trafficLines = trafficLines;
    }

    public LinkedHashSet<Station> getStops() {
        return stops;
    }

    public void setStops(LinkedHashSet<Station> stops) {
        this.stops = stops;
    }

    public LinkedHashSet<Line> getTrafficLines() {
        return trafficLines;
    }

    public void setTrafficLines(LinkedHashSet<Line> trafficLines) {
        this.trafficLines = trafficLines;
    }
}
