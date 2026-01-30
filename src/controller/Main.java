package controller;

import exception.DuplicateResourceException;
import exception.ResourceNotFoundException;
import model.Customer;
import model.RegularTicket;
import model.Ticket;
import model.VipTicket;
import repository.JdbcTicketRepository;
import repository.interfaces.TicketRepository;
import service.TicketService;
import utils.DatabaseConnection;
import utils.SortingUtils;
import utils.ReflectionUtils;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("DB CONNECTION FAILED -> stop program");
            return;
        }

        TicketRepository ticketRepository = new JdbcTicketRepository(connection);
        TicketService ticketService = new TicketService(ticketRepository);

        System.out.println("=== CREATE TICKETS ===");

        Customer c1 = new Customer(1);
        Customer c2 = new Customer(2);

        Ticket t1 = new RegularTicket(0, c1, 1, 2000);
        Ticket t2 = new VipTicket(0, c2, 2, 2000, 500);

        try {
            ticketService.createTicket(t1);
            ticketService.createTicket(t2);
            System.out.println("Tickets created successfully.");
        } catch (DuplicateResourceException e) {
            System.out.println("Expected validation error (duplicate): " + e.getMessage());
        }

        System.out.println("\n=== ALL TICKETS ===");
        List<Ticket> all = ticketService.getAllTickets();
        for (Ticket t : all) {
            System.out.println(t);
        }

        System.out.println("\n=== GET TICKET BY ID = 1 ===");
        try {
            System.out.println(ticketService.getTicketById(1));
        } catch (ResourceNotFoundException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== DELETE TICKET ID = 1 ===");
        try {
            ticketService.deleteTicket(1);
            System.out.println("Ticket id=1 deleted.");
        } catch (ResourceNotFoundException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== MOST EXPENSIVE TICKET ===");
        try {
            System.out.println(ticketService.getMostExpensiveTicket());
        } catch (ResourceNotFoundException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n=== SORTED TICKETS BY FINAL PRICE (ASC) ===");
        List<Ticket> tickets = ticketService.getAllTickets();
        SortingUtils.sortByFinalPriceAsc(tickets);

        System.out.println("\n=== REFLECTION DEMO ===");
        ReflectionUtils.inspectObject(t2);

        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }
}