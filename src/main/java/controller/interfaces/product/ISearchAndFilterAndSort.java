package controller.interfaces.product;

import controller.Exceptions;
import model.Product;

import java.util.List;

public interface ISearchAndFilterAndSort {

    List<Product> searchForAProductByName(String name, String token);

    List<Product> getProductsBasedOnFiltersAndSortsForOnePage(String token);

    void addAFilter(String filter, String token) throws Exceptions.InvalidFiledException;

    List<String> getAvailableFilter(String token);

    void removeAFilter(String filter, String token) throws Exceptions.InvalidFiledException;

    List<String> getCurrentActiveFilters(String token);

    void addASort(String sort, String token) throws Exceptions.InvalidFiledException;

    List<String> getAvailableSort(String token);

    void removeASort(String sort, String token) throws Exceptions.InvalidFiledException;

    List<String> getCurrentActiveSort(String token);

}
