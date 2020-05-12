package model;

import exception.NotEnoughCreditException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    // TODO: define order
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private List<Order> orders;

    // TODO: define promo
    //private List<Promo> availablePromos;


    public Customer() {
    }

    public Customer(String username, String password, String email, Role role) {
        super(username, password, email, role);
//        orders = new ArrayList<>();
//        availablePromos = new ArrayList<>();
    }


//    public List<Order> getOrders() {
//        return orders;
//    }

//    public List<Promo> getAvailablePromos() {
//        return availablePromos;
//    }

//    public void addOrder(Order order) {
//        orders.add(order);
//    }
}
