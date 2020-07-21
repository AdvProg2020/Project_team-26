package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.AuctionState;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;

public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    private ProductSeller productSeller;
    @JsonIgnore
    HashMap<Integer, Long> suggestedPricesByUsers;
    @JsonIgnore
    private AuctionState auctionState;

    public Auction() {
        suggestedPricesByUsers = new HashMap<>();
        auctionState = AuctionState.ACTIVE;
    }

    @JsonCreator
    public Auction(@JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate, @JsonProperty("productSeller") ProductSeller productSeller) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.productSeller = productSeller;
    }

    public int getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public HashMap<Integer, Long> getSuggestedPricesByUsers() {
        return suggestedPricesByUsers;
    }
}
