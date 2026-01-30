package repository.interfaces;

import model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    boolean existsByCustomerMovieType(int customerId, int movieId, String type);
}