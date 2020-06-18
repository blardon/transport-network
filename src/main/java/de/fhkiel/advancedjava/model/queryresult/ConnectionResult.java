package de.fhkiel.advancedjava.model.queryresult;

import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.math.BigDecimal;
import java.util.ArrayList;

@QueryResult
public class ConnectionResult {

    private ArrayList<Station> stations;
    private ArrayList<Leg> legs;
    private ArrayList<Line> lines;

    private BigDecimal totalCost;
    private Long totalTime;

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
}
