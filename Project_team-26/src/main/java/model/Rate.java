package model;

public class Rate {

    private int id;
    private Customer customer;
    private double score;
    private Product product;

    public Rate(int id) {
        this.id = id;
    }

    public Rate(int id, Customer customer, double score, Product product) {
        this.id = id;
        this.customer = customer;
        this.score = score;
        this.product = product;
    }
}
