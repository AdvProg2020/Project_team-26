package repository;

import model.Customer;
import model.Product;
import model.Rate;

public interface RateRepository extends Repository<Rate> {
    Rate getCustomerRate(int customerId, int productId);
}
