package repository.mysql;

import model.Rate;
import repository.RateRepository;

public class MySQLRateRepository
    extends MySQLRepository<Rate> implements RateRepository {
    public MySQLRateRepository() {
        super(Rate.class);
    }
}
