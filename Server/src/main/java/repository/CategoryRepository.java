package repository;

import model.Category;

public interface CategoryRepository extends Repository<Category> {
    Category getByName(String name);
}
