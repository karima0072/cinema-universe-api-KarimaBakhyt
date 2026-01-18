package model;

public class Customer {
    private int id;
    private String name;

    public Customer(int id) {
        this.id = id;
        this.name = "Unknown"; // можно позже заменить
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.name = name;
    }
}