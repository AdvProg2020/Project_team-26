package controller.product;

import model.Product;

import java.util.ArrayList;

public class SearchAndFilter {

    private static ArrayList<String> filters;

    SearchAndFilter() {
        filters = new ArrayList<>();
    }

    public Product[] searchForAProductByName(String name,String token) {
        return null;
    }

    public Product[] addAFilter(String filter,String token) {
        return null;
    }

    public Product[] getProductsBasedOnFiltersForOnePage(String token) {
        return null;
    }

    public Product[] removeAFilter(String filter, String token) {
        return null;
    }
}
