package model;

public class Comment {

    private int id;
    private Customer customer;
    private Product product;
    private String text;
    private CommentState state;

    public String getText() {
        return text;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Comment(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment(Customer customer, Product product, String text) {
        this.customer = customer;
        this.product = product;
        this.text = text;
    }
}
