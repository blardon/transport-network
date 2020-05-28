package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.relationship.TransferTo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransferToRepository extends Neo4jRepository<TransferTo, Long> {

    @Query("MATCH (stop:Stop)-[r:TRANSFER_TO]->(station:Station {name:$stationName}) RETURN COLLECT(r)")
    Collection<TransferTo> findAllToStationByStationName(String stationName);


}
