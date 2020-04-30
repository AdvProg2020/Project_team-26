package controller.interfaces.product;

import model.Product;

public interface ISearchAndFilter {

    Product[] searchForAProductByName(String name, String token);

    Product[] addAFilter(String filter,String token);

    Product[] getProductsBasedOnFiltersForOnePage(String token);

    Product[] removeAFilter(String filter, String token);
}
