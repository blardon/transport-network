package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public interface StationRepository extends Neo4jRepository<Station, Long> {

    @Query( "MATCH (from:Station {name:$fromStationName}), (to:Station {name:$toStationName})" +
            " CALL apoc.algo.dijkstra(from, to, 'HAS_STOP|HAS_LEG>|CONNECTING_TO>', 'time') yield path as path" +
            " RETURN nodes(path) AS nodes, relationships(path) AS relationships"
    )
    Iterable<Map<String, Object>> findFastestPathWithoutTransferTime(String fromStationName, String toStationName);

    @Query( " MATCH (start:Station {name: $fromStationName}), (end:Station {name: $toStationName})" +
            " CALL gds.alpha.shortestPath.stream({" +
            "   nodeQuery: 'MATCH (n) WHERE n:Station OR (n:Stop)-[:TRANSFER_TO]->(:Station {state:\"OPENED\"}) OR (n:Leg AND n.state=\"OPENED\") RETURN id(n) AS id'," +
            "   relationshipQuery: 'MATCH (n)-[r:HAS_STOP|HAS_LEG|TRANSFER_TO|CONNECTING_TO]->(m) " +
            "                       WHERE (n:Station OR (n:Stop)-[:TRANSFER_TO]->(:Station {state:\"OPENED\"}) OR (n:Leg AND n.state=\"OPENED\") ) AND (m:Station OR (m:Stop)-[:TRANSFER_TO]->(:Station {state:\"OPENED\"}) OR (m:Leg AND m.state=\"OPENED\") )" +
            "                       RETURN id(n) AS source, id(m) AS target, coalesce(r.time, 0.0) AS time'," +
            "   startNode: start," +
            "   endNode: end," +
            "   relationshipWeightProperty: 'time'" +
            " })" +
            " YIELD nodeId, cost" +
            " WITH COLLECT(gds.util.asNode(nodeId)) as pathNodes" +
            " MATCH (x)-[r]->(y)" +
            " WHERE x in pathNodes AND y in pathNodes" +
            " WITH [station in pathNodes where station:Station] as stations, [stop in pathNodes where stop:Stop] as stops, [leg in pathNodes where leg:Leg] as legs, collect(r) as relationships" +
            " UNWIND legs as leg" +
            " MATCH (leg)<-[:CARRIES_OUT]-(line:Line)" +
            " WITH COLLECT(DISTINCT line) as lines, stops, legs, relationships" +
            " UNWIND stops as stop" +
            " MATCH (stop)<-[r:HAS_STOP]-(station:Station)" +
            " RETURN COLLECT(DISTINCT station) as stations, lines, stops, legs, relationships, COLLECT(r)"
    )
    ConnectionResult findFastestPathWithTransferTime(String fromStationName, String toStationName);

}
