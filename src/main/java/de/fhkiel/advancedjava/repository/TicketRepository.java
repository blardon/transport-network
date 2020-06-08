package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.Ticket;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends Neo4jRepository<Ticket, Long> {
}
