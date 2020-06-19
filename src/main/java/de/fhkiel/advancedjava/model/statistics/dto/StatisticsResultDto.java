package de.fhkiel.advancedjava.model.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResultDto {

    @JsonProperty("totalNumberOfTicketsBought")
    private Long totalNumberOfTicketsBought;

    @JsonProperty("totalNumberOfDisturbancesCreated")
    private Long totalNumberOfDisturbancesCreated;

    @JsonProperty("perConnectionList")
    private List<LegStatisticsDto> perConnectionList;

    @JsonProperty("perStopList")
    private List<StationStatisticsDto> perStopList;

    @JsonProperty("totalNumberOfStops")
    private Long totalNumberOfStops;

    @JsonProperty("totalNumberOfBusStops")
    private Long totalNumberOfBusStops;

    @JsonProperty("totalNumberOfSubwayStops")
    private Long totalNumberOfSubwayStops;

    @JsonProperty("totalNumberOfSuburbanTrainStops")
    private Long totalNumberOfSuburbanTrainStops;

    @JsonProperty("totalNumberOfConnections")
    private Long totalNumberOfConnections;

    public Long getTotalNumberOfTicketsBought() {
        return totalNumberOfTicketsBought;
    }

    public void setTotalNumberOfTicketsBought(Long totalNumberOfTicketsBought) {
        this.totalNumberOfTicketsBought = totalNumberOfTicketsBought;
    }

    public Long getTotalNumberOfDisturbancesCreated() {
        return totalNumberOfDisturbancesCreated;
    }

    public void setTotalNumberOfDisturbancesCreated(Long totalNumberOfDisturbancesCreated) {
        this.totalNumberOfDisturbancesCreated = totalNumberOfDisturbancesCreated;
    }

    public List<LegStatisticsDto> getPerConnectionList() {
        return perConnectionList;
    }

    public void setPerConnectionList(List<LegStatisticsDto> perConnectionList) {
        this.perConnectionList = perConnectionList;
    }

    public List<StationStatisticsDto> getPerStopList() {
        return perStopList;
    }

    public void setPerStopList(List<StationStatisticsDto> perStopList) {
        this.perStopList = perStopList;
    }

    public Long getTotalNumberOfStops() {
        return totalNumberOfStops;
    }

    public void setTotalNumberOfStops(Long totalNumberOfStops) {
        this.totalNumberOfStops = totalNumberOfStops;
    }

    public Long getTotalNumberOfBusStops() {
        return totalNumberOfBusStops;
    }

    public void setTotalNumberOfBusStops(Long totalNumberOfBusStops) {
        this.totalNumberOfBusStops = totalNumberOfBusStops;
    }

    public Long getTotalNumberOfSubwayStops() {
        return totalNumberOfSubwayStops;
    }

    public void setTotalNumberOfSubwayStops(Long totalNumberOfSubwayStops) {
        this.totalNumberOfSubwayStops = totalNumberOfSubwayStops;
    }

    public Long getTotalNumberOfSuburbanTrainStops() {
        return totalNumberOfSuburbanTrainStops;
    }

    public void setTotalNumberOfSuburbanTrainStops(Long totalNumberOfSuburbanTrainStops) {
        this.totalNumberOfSuburbanTrainStops = totalNumberOfSuburbanTrainStops;
    }

    public Long getTotalNumberOfConnections() {
        return totalNumberOfConnections;
    }

    public void setTotalNumberOfConnections(Long totalNumberOfConnections) {
        this.totalNumberOfConnections = totalNumberOfConnections;
    }
}