package model.repository;

import model.Category;

import java.util.List;

public interface CategoryRepository extends Repository {
    Category getByName(String name);


    void edit(Category category, String Filed, String replacement);


}
