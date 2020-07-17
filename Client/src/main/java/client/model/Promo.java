package client.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "promo")
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id", unique = true)
    private int id;

    @Column(name = "promo_code", unique = true)
    private String promoCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "discount_percent", nullable = false)
    private double percent;

    @Column(name = "max_discount", nullable = false)
    private long maxDiscount;

    @Column(name = "max_valid_use", nullable = false)
    private int maxValidUse;

    @ManyToMany
    @JoinTable(
            name = "user_promo",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

    public Promo() {
        customers = new ArrayList<>();
    }

    public Promo(String code, Customer customer) {
        this.promoCode = code;
        customers = new ArrayList<Customer>();
        customers.add(customer);
    }

    public int getId() {
        return id;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getPercent() {
        return percent;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public int getMaxValidUse() {
        return maxValidUse;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public boolean isAvailable() {
        // TODO: This function must tell if promo is available now
        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setMaxValidUse(int maxValidUse) {
        this.maxValidUse = maxValidUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promo promo = (Promo) o;
        return id == promo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
