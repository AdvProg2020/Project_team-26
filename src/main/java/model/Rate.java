package model;

import javax.persistence.*;

@Entity
@Table(name = "rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id", unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @Column(name = "score", nullable = false)
    private double score;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Rate() {
    }

    public Rate(int id) {
        this.id = id;
    }

    public Rate(Customer customer, double score, Product product) {
        this.customer = customer;
        this.score = score;
        this.product = product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return this.customer.getId();
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return this.id;
    }

    public double getScore() {
        return this.score;
    }
}
