package repository;

import model.Product;
import model.Rate;
import model.User;

public interface RateRepository extends Repository<Rate> {
    public void update(Rate rate); //todo
    public boolean hasTheCustomerRatedBefore(User user, Product product);

}
