package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.schedule.Station;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StationRepository extends Neo4jRepository<Station, Long> {

    Optional<Station> findStationByName(String name);

    @Query("MATCH (station:Station {name:$name})-[r:HAS_STOP|TRANSFER_TO]-(stop:Stop) RETURN station, COLLECT(stop), COLLECT(r)")
    Optional<Station> findStationByNameWithStops(String name);

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
            " WITH COLLECT(gds.util.asNode(nodeId)) as pathNodes, COLLECT(DISTINCT cost) as costs" +
            " MATCH (x)-[r]->(y)" +
            " WHERE x in pathNodes AND y in pathNodes" +
            " WITH [station in pathNodes where station:Station] as stations, [stop in pathNodes where stop:Stop] as stops, [leg in pathNodes where leg:Leg] as legs, collect(r) as relationships, costs" +
            " UNWIND legs as leg" +
            " MATCH (leg)<-[co:CARRIES_OUT]-(line:Line)" +
            " WITH COLLECT(DISTINCT line) as lines, stops, legs, relationships, COLLECT(co) as cos, costs, stations" +
            " UNWIND stops as stop" +
            " MATCH (stop)-[r:HAS_STOP|TRANSFER_TO]-(station:Station)" +
            " RETURN COLLECT(DISTINCT station) as stations, lines, stops, legs, relationships, COLLECT(r), cos, toInteger(last(costs)) as totalTime"
    )
    Optional<ConnectionResult> findFastestPathWithTransferTime(String fromStationName, String toStationName);

    @Query( " MATCH p=(start:Station)-[:HAS_STOP|TRANSFER_TO|HAS_LEG|CONNECTING_TO*1..]->(end:Station)" +
            " WHERE NONE (x in nodes(p) WHERE ( (x:Stop)-[:TRANSFER_TO]->(:Station {state:'CLOSED'}) ) OR ( (x:Stop)-[:TRANSFER_TO]->(:Station {state:'OUT_OF_ORDER'}) ) )" +
            " AND NONE (x in nodes(p) WHERE x:Leg and (x.state='CLOSED' OR x.state='OUT_OF_ORDER') )" +
            " WITH p" +
            " WITH [r in relationships(p)] as relationships, [stop in nodes(p) WHERE stop:Stop] as stops, p" +
            " UNWIND relationships as r " +
            " WITH SUM (CASE WHEN r.time IS NULL THEN 0 ELSE r.time END) as totalTimeForPath, p, stops " +
            " UNWIND stops as stop " +
            " MATCH (stop)-[hs:HAS_STOP|TRANSFER_TO]-(station:Station) " +
            " WITH COUNT(DISTINCT station) as stationCount, COLLECT(DISTINCT station) as stations, totalTimeForPath, p, stops, collect(hs) as hs " +
            " WHERE stationCount >= $stops AND totalTimeForPath <= $minutes " +
            " WITH [leg in nodes(p) WHERE leg:Leg] as legs, stations, totalTimeForPath, stationCount, p, stops, hs " +
            " UNWIND legs as leg " +
            " MATCH (leg)<-[co:CARRIES_OUT]-(line:Line) " +
            " WITH COLLECT(DISTINCT line) as lines, COLLECT(co) as cos, stations, legs, totalTimeForPath, stationCount, p, stops, hs " +
            " RETURN stops, hs, stations, lines, legs, cos, totalTimeForPath as totalTime, stationCount, relationships(p) " +
            " ORDER BY stationCount" +
            " LIMIT 1")
    Optional<ConnectionResult> findNStopsInMMinutes(Long stops, Long minutes);

    @Query( " MATCH p=(start:Station {name:$fromStationName})-[:HAS_STOP|TRANSFER_TO|HAS_LEG|CONNECTING_TO*1..]->(end:Station {name:$toStationName})" +
            " WHERE NONE (x in nodes(p) WHERE (x:Stop)-[:TRANSFER_TO]->(:Station {state:'CLOSED'}) )" +
            " AND NONE (x in nodes(p) WHERE x:Leg and (x.state='CLOSED' OR x.state='OUT_OF_ORDER') )" +
            " WITH p" +
            " MATCH (leg:Leg)<-[co:CARRIES_OUT]-(line:Line) WHERE leg in nodes(p)" +
            " WITH p, COLLECT(DISTINCT line) as lines, COLLECT(DISTINCT co) as cos" +
            " UNWIND NODES(p) as n" +
            " WITH lines, cos, p, SIZE(COLLECT(DISTINCT n)) as testLength" +
            " WHERE testLength = LENGTH(p) + 1" +
            " WITH lines, cos, [leg in nodes(p) WHERE leg:Leg] as legs, p" +
            " UNWIND legs as leg" +
            " WITH lines, cos, SUM (CASE WHEN toFloat(leg.cost) IS NULL THEN 0 ELSE toFloat(leg.cost) END) as totalPriceForPath, p, legs" +
            " WITH lines, cos, legs, totalPriceForPath, p, [stop in nodes(p) WHERE stop:Stop] as stops" +
            " UNWIND stops as stop" +
            " MATCH (stop)-[hs:HAS_STOP|TRANSFER_TO]-(station:Station)" +
            " RETURN lines, cos, legs, COLLECT(DISTINCT station) as stations, stops, COLLECT(DISTINCT hs) as hs, relationships(p), [r in relationships(p) WHERE TYPE(r) = 'TRANSFER_TO'] as transfers, [r in relationships(p) WHERE TYPE(r) = 'CONNECTING_TO'] as connections, totalPriceForPath, p" +
            " ORDER BY totalPriceForPath" +
            " LIMIT 3")
    Iterable<ConnectionResult> find3LowestCosts(String fromStationName, String toStationName);

}
