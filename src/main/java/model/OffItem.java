package model;

public class OffItem implements Savable {

    private int id;
    private boolean isLoaded;
    private Product product;
    private long priceInOff;

    public OffItem(int id) {
        this.id = id;
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
