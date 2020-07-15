package repository;

import model.Customer;

import java.util.List;

public interface CustomerRepository extends Repository<Customer> {

    List<Customer> getAllProductBuyers(int productId, Pageable pageable);
}
