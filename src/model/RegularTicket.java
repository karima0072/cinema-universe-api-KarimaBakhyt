package model;

public class RegularTicket extends Ticket {

    public RegularTicket(int id, Customer customer, int movieId, double basePrice) {
        super(id, customer, movieId, basePrice);
    }

    @Override
    public String getType() {
        return "REGULAR";
    }

    @Override
    public double getFinalPrice() {
        return basePrice;
    }
}