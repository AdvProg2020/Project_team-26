package view.customer.orders;

import controller.interfaces.order.IOrderController;
import controller.interfaces.review.IRatingController;
import exception.*;
import model.Order;
import view.*;
import view.filterAndSort.OrderFilter;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class OrdersIView extends View implements IView {
    private EnumSet<OrdersViewValidCommands> validCommands;
    private IOrderController orderController;
    private IRatingController ratingController;
    private OrderFilter orderFilter;


    public OrdersIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
        orderFilter = new OrderFilter(manager);
    }

    @Override
    public void run() {
        showAll();
        while (!(super.input = (manager.scan.nextLine()).trim()).matches("exit")) {
            for (OrdersViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    break;
                }
            }
        }
    }

    protected void showAll() {
        try {
            orderController.getOrdersWithFilter(orderFilter.getFilterForController(), orderFilter.getFieldNameForSort(), orderFilter.isAscending(), manager.getToken()).forEach(order ->
                    manager.inputOutput.println("the order ID is " + order.getId() + " at " + order.getDate().toString() +
                            " with total price " + order.getTotalPrice()));
        } catch (NoAccessException e) {
            manager.inputOutput.println(e.getMessage());
        } catch (InvalidTokenException e) {
            manager.setTokenFromController(e.getMessage());
        }

    }

    protected void rateTheProductWithItsId(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            try {
                ratingController.addARating(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)), manager.getToken());
            } catch (NoAccessException | NotBoughtTheProductException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            }
            return;
        }
        manager.inputOutput.println("enter the number plz.");

    }

    protected void showOrdersWithIdToBuyer(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsInteger(matcher.group(1))) {
            try {
                Order order = orderController.getASingleOrder(Integer.parseInt(matcher.group(1)), manager.getToken());
                manager.inputOutput.println("the order ID is " + order.getId() + " at " + order.getDate().toString() +
                        " with total price " + order.getTotalPrice());
                order.getItems().forEach(orderItem -> manager.inputOutput.println("you have bough "
                        + orderItem.getProduct().getName()
                        + " with id : " + orderItem.getProductId() + " with total paid bill : "
                        + orderItem.getPaidPrice() + " and amount: " +
                        orderItem.getAmount()));
                return;
            } catch (NoAccessException | InvalidIdException | NoObjectIdException e) {
                manager.inputOutput.println(e.getMessage());
            } catch (InvalidTokenException e) {
                manager.setTokenFromController(e.getMessage());
            }
        }
        manager.inputOutput.println("enter the number plz.");
    }
}
