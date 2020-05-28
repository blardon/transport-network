package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.node.Leg;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegRepository extends Neo4jRepository<Leg, Long> {
}
