package de.fhkiel.advancedjava.model.queryresult;

import de.fhkiel.advancedjava.model.node.Leg;
import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.node.Stop;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.ArrayList;
import java.util.Set;

@QueryResult
public class ConnectionResult {

    private ArrayList<Stop> stops;
    private ArrayList<Leg> legs;
    private ArrayList<Line> lines;

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

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }
}
