package model;

public abstract class Ticket implements Validatable, PricedItem {

    protected int id;
    protected Customer customer;
    protected int movieId;
    protected double basePrice;

    public Ticket(int id, Customer customer, int movieId, double basePrice) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (movieId <= 0) {
            throw new IllegalArgumentException("movieId must be > 0");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("basePrice must be > 0");
        }

        this.id = id;
        this.customer = customer;
        this.movieId = movieId;
        this.basePrice = basePrice;
    }

    // === ABSTRACT METHODS (POLYMORPHISM) ===
    public abstract String getType();
    public abstract double getFinalPrice();

    public String shortInfo() {
        return "Ticket{" +
                "id=" + id +
                ", type='" + getType() + '\'' +
                ", customerId=" + customer.getId() +
                ", movieId=" + movieId +
                ", basePrice=" + basePrice +
                ", finalPrice=" + getFinalPrice() +
                '}';
    }


    @Override
    public String toString() {
        return shortInfo();
    }

    // === GETTERS ===
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    // === SETTERS ===
    public void setMovieId(int movieId) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("movieId must be > 0");
        }
        this.movieId = movieId;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("basePrice must be > 0");
        }
        this.basePrice = basePrice;
    }
}