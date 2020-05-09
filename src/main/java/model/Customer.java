package model;

import exception.NotEnoughCreditException;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Order> orders;
    private List<Promo> availablePromos;
    private long credit;

    public Customer(String username, String password, String email, Role role) {
        super(username, password, email, role);
        orders = new ArrayList<>();
        availablePromos = new ArrayList<>();
    }


    public List<Order> getOrders() {
        return orders;
    }

    public List<Promo> getAvailablePromos() {
        return availablePromos;
    }

    public long getCredit() {
        return credit;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void pay(long amount) throws NotEnoughCreditException {
        if(amount > credit) {
            throw new NotEnoughCreditException("You don't have enough creadit to pay " + amount, credit);
        }

        credit -= amount;
    }
}
