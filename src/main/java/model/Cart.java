package model;

import java.io.StringWriter;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> products;
    private Promo usedPromo;
    private String address;
}
