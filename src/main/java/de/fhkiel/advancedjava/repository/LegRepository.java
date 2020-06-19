package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.schedule.StopType;
import de.fhkiel.advancedjava.model.schedule.Leg;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegRepository extends Neo4jRepository<Leg, Long> {

    @Query(" MATCH (:Station {name:$fromStation})-[:HAS_STOP]->(:Stop)-[:HAS_LEG]->(leg:Leg)-[:CONNECTING_TO]->(:Stop)-[:TRANSFER_TO]->(:Station {name:$toStation})" +
            " WITH leg " +
            " MATCH (leg)<-[:CARRIES_OUT]-(line:Line {type:$type}) " +
            " RETURN leg")
    Optional<Leg> findLegByTypeBetweenStations(StopType type, String fromStation, String toStation);

}
