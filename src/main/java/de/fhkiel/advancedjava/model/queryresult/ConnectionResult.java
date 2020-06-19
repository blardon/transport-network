package de.fhkiel.advancedjava.model.queryresult;

import de.fhkiel.advancedjava.model.relationship.ConnectingTo;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import de.fhkiel.advancedjava.model.schedule.Leg;
import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.schedule.Stop;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * A ConnectionResult is the result of a connection finding and contains all stations, stops, legs, lines and the totalCost/totalTime of the connection.
 *
 * @author Bennet v. Lardon
 */
@QueryResult
public class ConnectionResult {

    private List<Station> stations;
    private List<Stop> stops;
    private List<Leg> legs;
    private List<Line> lines;
    private List<TransferTo> transfers;
    private List<ConnectingTo> connections;

    private BigDecimal totalCost;
    private Long totalTime;

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public void setStations(List<Station> stations) {
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

    public List<TransferTo> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferTo> transfers) {
        this.transfers = transfers;
    }

    public List<ConnectingTo> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectingTo> connections) {
        this.connections = connections;
    }
}
