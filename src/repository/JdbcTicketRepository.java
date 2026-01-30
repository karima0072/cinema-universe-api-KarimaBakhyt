package repository;

import repository.interfaces.TicketRepository;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.Customer;
import model.RegularTicket;
import model.Ticket;
import model.VipTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTicketRepository implements TicketRepository {

    private final Connection connection;

    public JdbcTicketRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Ticket ticket) {
        String sql = "INSERT INTO tickets (customer_id, movie_id, type, base_price, final_price) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ticket.getCustomer().getId());
            ps.setInt(2, ticket.getMovieId());
            ps.setString(3, ticket.getType());
            ps.setDouble(4, ticket.getBasePrice());
            ps.setDouble(5, ticket.getFinalPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create ticket", e);
        }
    }

    @Override
    public List<Ticket> getAll() {
        String sql = "SELECT ticket_id, customer_id, movie_id, type, base_price, final_price FROM tickets";
        List<Ticket> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToTicket(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get all tickets", e);
        }
    }

    @Override
    public Ticket getById(Integer id) {
        String sql = "SELECT ticket_id, customer_id, movie_id, type, base_price, final_price " +
                "FROM tickets WHERE ticket_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Integer auto-unboxing -> int

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new ResourceNotFoundException("Ticket not found with id=" + id);
                }
                return mapRowToTicket(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get ticket by id=" + id, e);
        }
    }

    @Override
    public void update(Integer id, Ticket updated) {
        // Проверим, что существует
        getById(id);

        String sql = "UPDATE tickets " +
                "SET customer_id = ?, movie_id = ?, type = ?, base_price = ?, final_price = ? " +
                "WHERE ticket_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, updated.getCustomer().getId());
            ps.setInt(2, updated.getMovieId());
            ps.setString(3, updated.getType());
            ps.setDouble(4, updated.getBasePrice());
            ps.setDouble(5, updated.getFinalPrice());
            ps.setInt(6, id); // Integer -> int

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update ticket id=" + id, e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        getById(id);

        String sql = "DELETE FROM tickets WHERE ticket_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id); // Integer -> int
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete ticket id=" + id, e);
        }
    }

    @Override
    public boolean existsByCustomerMovieType(int customerId, int movieId, String type) {
        String sql = """
            SELECT COUNT(*)
            FROM tickets
            WHERE customer_id = ? AND movie_id = ? AND type = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, movieId);
            ps.setString(3, type);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check duplicate ticket", e);
        }
    }

    private Ticket mapRowToTicket(ResultSet rs) throws SQLException {
        int ticketId = rs.getInt("ticket_id");
        int customerId = rs.getInt("customer_id");
        int movieId = rs.getInt("movie_id");
        String type = rs.getString("type");
        double base = rs.getDouble("base_price");
        double fin = rs.getDouble("final_price");

        Customer customer = new Customer(customerId);

        if ("VIP".equalsIgnoreCase(type)) {
            double vipFee = fin - base;
            if (vipFee < 0) vipFee = 0;
            return new VipTicket(ticketId, customer, movieId, base, vipFee);
        } else {
            return new RegularTicket(ticketId, customer, movieId, base);
        }
    }
}