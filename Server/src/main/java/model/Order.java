package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@SecondaryTable(name = "order_price", pkJoinColumns = @PrimaryKeyJoinColumn(name = "order_id"))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", unique = true)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "total_price", table = "order_price", insertable = false, updatable = false)
    private Long totalPrice;

    @Column(name = "paid_amount", table = "order_price", insertable = false, updatable = false)
    private Long paidAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "promo_id")
    private Promo usedPromo;

    @Column(name = "discount")
    private long discount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Order() {
    }

    public Order(Customer customer, Promo usedPromo, String address) {
        this.customer = customer;
        this.usedPromo = usedPromo;
        this.address = address;
        this.items = new ArrayList<>();
        this.date = new Date();
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int getId() {
        return this.id;
    }

    public Order(Order order, Seller seller) {
        this.date = order.date;
        this.usedPromo = null;
        for (OrderItem item : order.items) {
            if (item.getSeller().getUsername().equals(seller.getUsername())) {
                items.add(item);
            }
        }
    }

    public Promo getUsedPromo() {
        return usedPromo;
    }

    public long getDiscount() {
        return discount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    public long calculateTotalPrice() {
        return items.stream().
                map(item -> item.getPrice()).
                reduce((price, totalPrice) -> price + totalPrice).
                orElse((long) 0);
    }

    public long calculatePaidAmount() {
        return items.stream().
                map(item -> item.getPaidPrice()).
                reduce((price, paidAmount) -> price + paidAmount).
                orElse((long) 0);
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setDiscount() {
        discount = calculateDiscount();
    }

    public long calculateDiscount() {
        if (usedPromo != null) {
            long paid = calculatePaidAmount();
            if ((long) (paid * usedPromo.getPercent() / 100) > usedPromo.getMaxDiscount()) {
                return usedPromo.getMaxDiscount();
            } else {
                return (long) (paid * usedPromo.getPercent() / 100);
            }
        }
        return  0;
    }

    public Date getDate() {
        return date;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }
}
