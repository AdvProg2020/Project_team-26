package repository.mysql;

import model.*;
import model.enums.RequestStatus;
import model.enums.RequestType;
import model.enums.Status;
import repository.OffRepository;
import repository.RequestRepository;

import java.util.Date;
import java.util.List;

public class MySQLOffRepository
        extends MySQLRepository<Off> implements OffRepository {

    RequestRepository requestRepository;

    public MySQLOffRepository() {
        super(Off.class);
        requestRepository = (RequestRepository) MySQLRepository.repositoryContainer.getRepository("RequestRepository");
    }

    @Override
    public void addRequest(Off off) {
        off.setStatus(Status.DEACTIVE);
        Request request = new Request(off.getSeller(), new Date(), RequestType.ADD, RequestStatus.PENDING);
        save(off);
        request.setOff(off);
        requestRepository.save(request);
    }

    @Override
    public void editRequest(Off off) {
        off.setStatus(Status.DEACTIVE);
        save(off);

        off.setId(0);
        off.getItems().forEach(offItem -> offItem.setId(0));
        off.setStatus(Status.DEACTIVE);
        Request request = new Request(off.getSeller(), new Date(), RequestType.ADD, RequestStatus.PENDING);
        request.setOff(off);
        requestRepository.save(request);
    }

    @Override
    public void deleteRequest(int id) {
        Off off = getById(id);
        Request request = new Request(off.getSeller(), new Date(), RequestType.DELETE, RequestStatus.PENDING);
        request.setOff(off);
        requestRepository.save(request);
    }

    @Override
    public List<Off> getAllOffForSellerWithFilter(String sortField, boolean isAscending, int SellerId) {
        return null;
    }
}
