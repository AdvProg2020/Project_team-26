package controller.product;

import model.OrderItem;
import model.Promo;

import java.util.Date;
import java.util.List;

public class OrderForm {

    private Date date;
    private long totalPrice;
    private long paidAmount;
    private Promo usedPromo;
    private int[] itemsIds;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Promo getUsedPromo() {
        return usedPromo;
    }

    public void setUsedPromo(Promo usedPromo) {
        this.usedPromo = usedPromo;
    }

    public int[] getItemsIds() {
        return itemsIds;
    }

    public void setItemsIds(int[] itemsIds) {
        this.itemsIds = itemsIds;
    }
}
