package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Order> orders;
    private List<Promo> availablePromos;
    private long credit;

    public Customer(int id) {
        super(id);
        orders = new ArrayList<Order>();
        availablePromos = new ArrayList<Promo>();
    }
}
