package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.Vehicle;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends Neo4jRepository<Vehicle, Long> {

}
