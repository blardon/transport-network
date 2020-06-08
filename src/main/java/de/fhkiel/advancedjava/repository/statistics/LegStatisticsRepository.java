package de.fhkiel.advancedjava.repository.statistics;

import de.fhkiel.advancedjava.model.statistics.LegStatistics;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegStatisticsRepository extends Neo4jRepository<LegStatistics, Long> {

    @Query( " MATCH (res:LegStatistics)-[:FOR_LEG]->(leg:Leg) " +
            " WHERE ID(leg) = $id " +
            " RETURN res")
    Optional<LegStatistics> findForLegById(Long id);

}
