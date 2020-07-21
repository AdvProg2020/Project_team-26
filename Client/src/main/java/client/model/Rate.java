package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rate {
    private int id;
    private User customer;
    private double score;
    private Product product;

    @JsonCreator
    public Rate(@JsonProperty("id") int id,
                @JsonProperty("customer") User customer,
                @JsonProperty("score") double score,
                @JsonProperty("product") Product product) {
        this.id = id;
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
