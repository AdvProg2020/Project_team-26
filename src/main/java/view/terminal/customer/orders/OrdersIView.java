package view.terminal.customer.orders;

import controller.interfaces.order.IOrderController;
import controller.interfaces.review.IRatingController;
import exception.*;
import model.Order;
import view.terminal.ControllerContainer;
import view.terminal.View;
import view.terminal.ViewManager;
import view.terminal.filterAndSort.OrderSort;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
        manager.inputOutput.println("orders menu :");
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
        } catch (NotLoggedINException e) {
            manager.inputOutput.println(e.getMessage());
            manager.loginInAllPagesEssential();
        }

    }

    protected void rateTheProductWithItsId(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
            if (manager.checkTheInputIsDouble(matcher.group(2))) {
                try {
                    ratingController.addRating(Double.parseDouble(matcher.group(2)), Integer.parseInt(matcher.group(1)), manager.getToken());
                } catch (NoAccessException | NotBoughtTheProductException e) {
                    manager.inputOutput.println(e.getMessage());
                } catch (InvalidTokenException e) {
                    manager.setTokenFromController(e.getMessage());
                }
                return;
            }
            manager.inputOutput.println("enter the valid rate plz.");
        } else
            manager.inputOutput.println("enter the valid id plz.");

    }

    protected void showOrdersWithIdToBuyer(Matcher matcher) {
        matcher.find();
        if (manager.checkTheInputIsIntegerOrLong(matcher.group(1), false)) {
            try {
                Order order = orderController.getASingleOrder(Integer.parseInt(matcher.group(1)), manager.getToken());
                manager.inputOutput.println("the order ID is " + order.getId() + " at " + order.getDate().toString() +
                        " with total price " + order.getTotalPrice());
                order.getItems().forEach(orderItem -> manager.inputOutput.println("you have bough "
                        + orderItem.getProduct().getName()
                        + " with id : " + orderItem.getProductId() + "\nwith total paid bill : "
                        + orderItem.getPaidPrice() + " actual price : " + orderItem.getPrice() + " and amount: " +
                        orderItem.getAmount() + " from " + orderItem.getSeller().getUsername()));
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

    protected void logOut() {
        manager.logoutInAllPages();
    }

    protected void help() {
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("back");
        commandList.add("logout");
        commandList.add("sorting");
        commandList.add("show order [orderId]");
        commandList.add("rate [productId] [1-5]");
        commandList.add("show all");
        commandList.forEach(i -> manager.inputOutput.println(i));
    }
}
