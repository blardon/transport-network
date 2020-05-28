package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.node.Line;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends Neo4jRepository<Line, Long> {
}
