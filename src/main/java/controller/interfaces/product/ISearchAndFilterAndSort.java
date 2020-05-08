package controller.interfaces.product;

import exception.WrongFieldException;
import model.Product;

import java.util.List;

public interface ISearchAndFilterAndSort {

    List<Product> searchForAProductByName(String name, String token);

    List<Product> getProductsBasedOnFiltersAndSortsForOnePage(String token);

    List<Product> addAFilter(String filter, String token) throws WrongFieldException;

    List<String> getAvailableFilter(String token);

    List<Product> removeAFilter(String filter, String token) throws WrongFieldException;

    List<String> getCurrentActiveFilters(String token);

    void addASort(String sort, String token) throws WrongFieldException;

    List<String> getAvailableSort(String token);

    void removeASort(String sort, String token) throws WrongFieldException;

    List<String> getCurrentActiveSort(String token);

}
