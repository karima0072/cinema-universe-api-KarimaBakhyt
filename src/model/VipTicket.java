package model;

public class VipTicket extends Ticket {
    private double vipFee;

    public VipTicket(int id, Customer customer, int movieId, double basePrice, double vipFee) {
        super(id, customer, movieId, basePrice);
        if (vipFee < 0) throw new IllegalArgumentException("vipFee must be >= 0");
        this.vipFee = vipFee;
    }

    public double getVipFee() {
        return vipFee;
    }

    @Override
    public String getType() {
        return "VIP";
    }

    @Override
    public double getFinalPrice() {
        return basePrice + vipFee;
    }

    @Override
    public void validate() {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive");
        }
        if (vipFee < 0) {
            throw new IllegalArgumentException("VIP fee must be >= 0");
        }
    }
}