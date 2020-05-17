package view.customer.orders;

import controller.interfaces.order.IOrderController;
import controller.interfaces.review.IRatingController;
import exception.*;
import model.Order;
import view.*;
import view.filterAndSort.OrderSort;

import java.util.EnumSet;
import java.util.regex.Matcher;

public class OrdersIView extends View {
    private EnumSet<OrdersViewValidCommands> validCommands;
    private IOrderController orderController;
    private IRatingController ratingController;
    private OrderSort orderSort;


    public OrdersIView(ViewManager manager) {
        super(manager);
        validCommands = EnumSet.allOf(OrdersViewValidCommands.class);
        orderSort = new OrderSort(manager);
        orderController = (IOrderController) manager.getController(ControllerContainer.Controller.OrderController);
        ratingController = (IRatingController) manager.getController(ControllerContainer.Controller.RatingController);
    }

    @Override
    public void run() {
        showAll();
        boolean isDone;
        while (!(super.input = (manager.inputOutput.nextLine()).trim()).matches("back") && manager.getIsUserLoggedIn()) {
            isDone = false;
            for (OrdersViewValidCommands command : validCommands) {
                if ((command.getStringMatcher(super.input).find())) {
                    command.goToFunction(this);
                    isDone = true;
                    break;
                }
            }
            if (isDone)
                manager.inputOutput.println("invalid input");
        }
    }

    protected void showAll() {
        try {
            orderController.getOrdersWithFilter(orderSort.getFieldNameForSort(), orderSort.isAscending(), manager.getToken()).forEach(order ->
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
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1))) {
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
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1))) {
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

    protected void sorting() {
        orderSort.run();
    }

    protected void filtering() {
        orderSort.run();
    }

    protected void help() {
        validCommands.forEach(command -> manager.inputOutput.println(command.toString()));
    }
}
