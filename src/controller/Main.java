package controller;

import model.Customer;
import model.RegularTicket;
import model.Ticket;
import model.VipTicket;
import repository.TicketRepository;
import service.TicketService;
import utils.DatabaseConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            TicketRepository ticketRepository = new TicketRepository(connection);
            TicketService ticketService = new TicketService(ticketRepository);

            System.out.println("=== CREATE TICKETS ===");

            Customer c1 = new Customer(1);
            Customer c2 = new Customer(2);

            Ticket t1 = new RegularTicket(0, c1, 1, 2000);
            Ticket t2 = new VipTicket(0, c2, 2, 2000, 500);

            ticketService.createTicket(t1);
            ticketService.createTicket(t2);

            System.out.println("\n=== ALL TICKETS (BEFORE DELETE) ===");
            for (Ticket t : ticketService.getAllTickets()) {
                System.out.println(t);
            }

            System.out.println("\n=== GET TICKET BY ID = 1 (BEFORE DELETE) ===");
            System.out.println(ticketService.getTicketById(1));

            System.out.println("\n=== DELETE TICKET ID = 1 ===");
            ticketService.deleteTicket(1);

            System.out.println("\n=== ALL TICKETS (AFTER DELETE) ===");
            for (Ticket t : ticketService.getAllTickets()) {
                System.out.println(t);
            }

            System.out.println("\n=== TRY GET TICKET BY ID = 1 (AFTER DELETE) ===");
            try {
                System.out.println(ticketService.getTicketById(1));
            } catch (Exception e) {
                System.out.println("Expected error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}