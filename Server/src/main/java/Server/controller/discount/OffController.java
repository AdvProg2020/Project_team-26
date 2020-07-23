package Server.controller.discount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.List;
import java.util.Map;

@RestController
public class OffController {
    ProductRepository productRepository;
    private OffRepository offRepository;
    private ProductSellerRepository productSellerRepository;

    public OffController() {
        productRepository = (ProductRepository) RepositoryContainer.getInstance().getRepository("ProductRepository");
        offRepository = (OffRepository) RepositoryContainer.getInstance().getRepository("OffRepository");
        productSellerRepository = (ProductSellerRepository) RepositoryContainer.getInstance().getRepository("ProductSellerRepository");
    }


    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You must be logged in.");
        } else if (!(session.getLoggedInUser().getRole() == Role.SELLER))
            throw new NoAccessException(message);
    }

    private Off getOffByIdWithCheck(int id) throws InvalidIdException {
        Off off = offRepository.getById(id);
        if (off == null)
            throw new InvalidIdException("the off with " + id + " doesn't exist");
        return off;
    }

    @PostMapping("/controller/method/off/create-new-off")
    public void createNewOff(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        Gson gson = new Gson();
        Off newOff = gson.fromJson((String) info.get("newOff"),Off.class);
        String token = (String) info.get("token");
        checkAccessOfUser(token, "seller can create a off");
        newOff.setSeller((Seller) Session.getSession(token).getLoggedInUser());
        offRepository.addRequest(newOff);
    }

    @PostMapping("/controller/method/off/add-product-to-off")
    public void addProductToOff(@RequestBody Map info) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        ObjectMapper objectMapper = new ObjectMapper();
        Off off = objectMapper.convertValue(info.get("off"), Off.class);
        int productId = (Integer) info.get("productId");
        long priceInOff = objectMapper.convertValue(info.get("priceInOff"),Long.class);
        double percent = (Double) info.get("percent");
        boolean isFirstTime = (Boolean) info.get("isFirstTime");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "Only Seller Can Add Product");
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
        ProductSeller productSeller = productSellerRepository.getProductSellerByIdAndSellerId(productId, seller.getId());
        Product product = productRepository.getById(productId);
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (product == null) {
            throw new InvalidIdException("No Such product Exists");
        } else if (offItem != null) {
            throw new ObjectAlreadyExistException("the product exist in your off list", product);
        } else if (productSeller == null) {
            throw new NoAccessException("you are not the seller of the product you have chosen.");
        } else if (priceInOff < 0) {
            offItem = new OffItem(productSeller, (long) ((long) productSeller.getPrice() * (100 - percent) / 100));
        } else {
            offItem = new OffItem(productSeller, priceInOff);
        }
        offItems.add(offItem);
        if (!isFirstTime) {
            off.setSeller((Seller) Session.getSession(token).getLoggedInUser());
            offRepository.addRequest(off);
        }

    }

    private OffItem getOffItem(List<OffItem> offItems, int productId) {
        for (OffItem offItem : offItems) {
            if (offItem.getProductSeller().getProduct().getId() == productId)
                return offItem;
        }
        return null;
    }

    private ProductSeller getProductSeller(int seller, List<ProductSeller> productSellers) {
        for (ProductSeller productSeller : productSellers) {
            if (productSeller.getSeller().getId() == seller)
                return productSeller;
        }
        return null;
    }
    @PostMapping("/controller/method/off/remove-product-from-off")
    public void removeProductFromOff(@RequestBody Map info) throws NoAccessException, ObjectAlreadyExistException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        Off off = (Off) info.get("off");
        int productId = (Integer) info.get("productId");
        boolean isForAdd = (Boolean) info.get("isForAdd");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only seller can add product");
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new InvalidIdException("no product exist");
        List<OffItem> offItems = off.getItems();
        OffItem offItem = getOffItem(offItems, productId);
        if (offItem == null)
            throw new ObjectAlreadyExistException("the product does not exist in list", product);
        ProductSeller productSeller = getProductSeller(Session.getSession(token).getLoggedInUser().getId(), product.getSellerList());
        if (productSeller == null)
            throw new NoAccessException("the product you have choose is not in your list");
        if (!isForAdd)
            offRepository.deleteRequest(off.getId());
    }

    @PostMapping("/controller/method/off/remove-a-off")
    public void removeAOff(@RequestBody Map info) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only seller can remove off");
        getOffByIdWithCheck(id);
        offRepository.deleteRequest(id);
    }

    @PostMapping("/controller/method/off/get-all-products-with-off")
    public List<Product> getAllProductWithOff(@RequestBody Map info) {
        Map<String,String> filter = (Map<String, String>) info.get("filter");
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        Pageable page = createAPage(sortField, isAscending, startIndex, endIndex);
        return productRepository.getAllSortedAndFiltered(filter, page);
    }

    @PostMapping("/controller/method/off/get-all-off-for-seller-with-filter")
    public List<Off> getAllOfForSellerWithFilter(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only seller");
        Pageable page = createAPage(sortField, isAscending, startIndex, endIndex);
        return ((Seller) Session.getSession(token).getLoggedInUser()).getAllOffs(page);
    }

    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if (isAscending) {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.DESCENDING);
        }
    }

    @GetMapping("/controller/method/off/get-off/{id}")
    public Off getOff(@PathVariable("id") int id) throws InvalidIdException {
        return getOffByIdWithCheck(id);
    }
    @PostMapping("/controller/method/off/edit")
    public void edit(@RequestBody Map info) throws NoAccessException, InvalidTokenException, InvalidIdException, NotLoggedINException {
        Off newOff = (Off) info.get("newOff");
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only seller");
        Off off = getOffByIdWithCheck(id);
        Seller seller = (Seller) Session.getSession(token).getLoggedInUser();
        // if (!seller.getAllOffs(new Pageable()).contains(off)) // TODO
        //   throw new NoAccessException("you can only change your off");
        off.setSeller((Seller) Session.getSession(token).getLoggedInUser());
        offRepository.addRequest(off);
    }
}
