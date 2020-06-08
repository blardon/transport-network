package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.exception.WrongInputException;
import de.fhkiel.advancedjava.model.Ticket;
import de.fhkiel.advancedjava.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket){
        return this.ticketRepository.save(Optional.ofNullable(ticket).orElseThrow( () -> new WrongInputException("No ticket supplied.")));
    }

}
