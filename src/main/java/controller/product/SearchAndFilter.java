package controller.product;

import controller.interfaces.product.ISearchAndFilterAndSort;
import exception.WrongFieldException;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchAndFilter implements ISearchAndFilterAndSort {

    private static ArrayList<String> filters;

    SearchAndFilter() {
        filters = new ArrayList<>();
    }

    public List<Product> searchForAProductByName(String name, String token) {
        return null;
    }

    public List<Product> addAFilter(String filter,String token) {
        return null;
    }

    @Override
    public List<String> getAvailableFilter(String token) {
        return null;
    }

    public List<Product> getProductsBasedOnFiltersAndSortsForOnePage(String token) {
        return null;
    }

    public List<Product> removeAFilter(String filter, String token) {
        return null;
    }

    @Override
    public List<String> getCurrentActiveFilters(String token) {
        return null;
    }

    @Override
    public void addASort(String sort, String token) throws WrongFieldException {

    }

    @Override
    public List<String> getAvailableSort(String token) {
        return null;
    }

    @Override
    public void removeASort(String sort, String token) throws WrongFieldException {

    }

    @Override
    public List<String> getCurrentActiveSort(String token) {
        return null;
    }
}
