package de.fhkiel.advancedjava.repository.statistics;

import de.fhkiel.advancedjava.model.statistics.LegStatistics;
import de.fhkiel.advancedjava.model.statistics.StationStatistics;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationStatisticsRepository extends Neo4jRepository<StationStatistics, Long> {

    @Query( " MATCH (res:StationStatistics)-[:FOR_STATION]->(station:Station {stationId:$id}) " +
            " RETURN res")
    Optional<StationStatistics> findForStationById(Long id);

}
