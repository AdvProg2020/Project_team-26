package repository;

import model.Category;
import model.Product;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends Repository<Category> {
    Category getByName(String name);
}
