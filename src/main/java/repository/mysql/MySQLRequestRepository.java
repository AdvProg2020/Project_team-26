package repository.mysql;

import model.Request;
import repository.RequestRepository;

public class MySQLRequestRepository
 extends MySQLRepository<Request> implements RequestRepository {
    public MySQLRequestRepository() {
        super(Request.class);
    }
}
