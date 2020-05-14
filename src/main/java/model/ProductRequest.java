package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_request")
public class ProductRequest {

    @Id
    @Column(name = "product_request_id", unique = true)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSeller> sellerList;
}
