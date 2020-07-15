package model;

import model.enums.CommentState;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@SecondaryTable(name = "comment_product_bought", pkJoinColumns = @PrimaryKeyJoinColumn(name = "comment_id"))
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", unique = true)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "has_bought", table = "comment_product_bought", insertable = false, updatable = false)
    private Boolean hasBought;

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

    public Comment(Customer customer, Product product, String text, String title) {
        this.customer = customer;
        this.product = product;
        this.text = text;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean hasBought() {
        if(hasBought == null) {
            return false;
        }
        return hasBought;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CommentState getState() {
        return state;
    }

    public void setState(CommentState state) {
        this.state = state;
    }
}
