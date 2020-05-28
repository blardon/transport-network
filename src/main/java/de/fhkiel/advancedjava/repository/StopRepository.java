package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.node.Stop;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopRepository extends Neo4jRepository<Stop, Long> {

    @Query("MATCH (station:Station {stationId:$stationId})-[r:HAS_STOP]->(stop:Stop {type:$type}) RETURN stop")
    Optional<Stop> findStopByTypeAtStationById(Long stationId, StopType type);

}
