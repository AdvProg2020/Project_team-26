package model;

public class Rate implements Savable {

    private int id;
    private boolean isLoaded;
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
        isLoaded = true;
    }


    @Override
    public void load() {
        if(!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
