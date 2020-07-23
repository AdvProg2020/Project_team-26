package repository.mysql;

import model.ProductFile;
import repository.ProductFileRepository;

public class MySQLProductFileRepository
        extends MySQLRepository<ProductFile> implements ProductFileRepository {

    public MySQLProductFileRepository() {
        super(ProductFile.class);
    }
}
