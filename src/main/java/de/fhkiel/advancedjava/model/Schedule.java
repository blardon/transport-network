package de.fhkiel.advancedjava.model;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;

import java.util.LinkedHashSet;

public class Schedule {

    private LinkedHashSet<Station> stations;
    private LinkedHashSet<Line> lines;

    public Schedule(LinkedHashSet<Station> stations, LinkedHashSet<Line> lines) {
        this.stations = stations;
        this.lines = lines;
    }

    public LinkedHashSet<Station> getStations() {
        return stations;
    }

    public void setStations(LinkedHashSet<Station> stations) {
        this.stations = stations;
    }

    public LinkedHashSet<Line> getLines() {
        return lines;
    }

    public void setLines(LinkedHashSet<Line> lines) {
        this.lines = lines;
    }
}
