package model;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", unique = true)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private CommentState state;

    public Comment() {

    }

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

    public int getId() {
        return this.id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment(Customer customer, Product product, String text, String title) {
        this.customer = customer;
        this.product = product;
        this.text = text;
        this.title = title;
    }
}
