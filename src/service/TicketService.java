package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Customer;
import model.Ticket;
import repository.TicketRepository;

import java.util.List;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public void createTicket(Ticket ticket) {
        validateTicket(ticket);
        preventDuplicate(ticket);
        ticketRepository.create(ticket);
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.getAll();
    }


    public Ticket getTicketById(int id) {
        validateId(id);
        return ticketRepository.getById(id);
    }


    public void updateTicket(int id, Ticket ticket) {
        validateId(id);
        validateTicket(ticket);

        ticketRepository.getById(id);

        ticketRepository.update(id, ticket);
    }


    public void deleteTicket(int id) {
        validateId(id);


        ticketRepository.getById(id);

        ticketRepository.deleteById(id);
    }



    private void validateId(int id) {
        if (id <= 0) {
            throw new InvalidInputException("id must be > 0");
        }
    }

    private void validateTicket(Ticket ticket) {
        if (ticket == null) {
            throw new InvalidInputException("Ticket cannot be null");
        }

        Customer customer = ticket.getCustomer();
        if (customer == null) {
            throw new InvalidInputException("Customer cannot be null");
        }
        if (customer.getId() <= 0) {
            throw new InvalidInputException("customerId must be > 0");
        }

        if (ticket.getMovieId() <= 0) {
            throw new InvalidInputException("movieId must be > 0");
        }

        if (ticket.getBasePrice() <= 0) {
            throw new InvalidInputException("basePrice must be > 0");
        }

        String type = ticket.getType();
        if (type == null || type.trim().isEmpty()) {
            throw new InvalidInputException("type cannot be empty");
        }

        double finalPrice = ticket.getFinalPrice();
        if (finalPrice <= 0) {
            throw new InvalidInputException("finalPrice must be > 0");
        }


        if (finalPrice < ticket.getBasePrice()) {
            throw new InvalidInputException("finalPrice cannot be less than basePrice");
        }
    }


    private void preventDuplicate(Ticket ticket) {
        List<Ticket> all = ticketRepository.getAll();
        for (Ticket t : all) {
            if (t.getCustomer().getId() == ticket.getCustomer().getId()
                    && t.getMovieId() == ticket.getMovieId()
                    && t.getType().equalsIgnoreCase(ticket.getType())) {
                throw new DuplicateResourceException("Duplicate ticket for same customer/movie/type");
            }
        }
    }
}
