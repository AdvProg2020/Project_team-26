package view.customer;

import view.*;
import view.customer.orders.OrdersView;

import java.util.regex.Matcher;

public class CustomerView extends View {
    private ViewManager manager;

    public CustomerView(ViewManager manager) {
        this.manager = manager;
    }

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    private void editTheField(Matcher matcher) {

    }

    private void personalInfo(Matcher matcher) {

    }

    private void promoCodes(Matcher matcher) {

    }

    private void customerInfo(Matcher matcher) {

    }

    private void orderView(Matcher matcher) {
        OrdersView ordersView = new OrdersView();
        ordersView.run(this.manager);
    }

    private void balance(Matcher matcher) {
    }

    private void cart(Matcher matcher) {

    }
}
