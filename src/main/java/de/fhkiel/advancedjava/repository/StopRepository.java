package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Stop;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopRepository extends Neo4jRepository<Stop, Long> {

    @Query("MATCH (station:Station {stationId:$stationId})-[r:HAS_STOP]->(stop:Stop {type:$type}) RETURN stop")
    Optional<Stop> findStopByTypeAtStationById(Long stationId, StopType type);

    @Query("MATCH (station:Station {name:$stationName})<-[r:TRANSFER_TO]-(stop:Stop {type:$type}) RETURN stop, r, station")
    Optional<Stop> findStopByTypeAtStationByName(String stationName, StopType type);

    Iterable<Stop> findStopsByType(StopType type);

}
