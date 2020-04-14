package view.customer;

import view.customer.orders.OrdersView;
import view.View;
import view.cart.CartView;

public enum CustomerInfoView {
    ViewPersonalInfo {
        @Override
        public View getView() {
            return new PersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        @Override
        public View getView() {
            return new EditTheField();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ViewCart {
        @Override
        public View getView() {
            return new CartView();
        }

        @Override
        public String toString() {
            return "view\\s+cart";
        }
    },

    ViewOrdersForBuyer {
        @Override
        public View getView() {
            return new OrdersView();
        }

        @Override
        public String toString() {
            return "view\\s+orders";
        }
    },
    ViewBalanceToBuyer {
        @Override
        public View getView() {

            return new PersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+balance";
        }
    },
    ViewDiscountCodesToBuyer {
        @Override
        public View getView() {
            return new PromoCodes();
        }

        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    };

    public abstract View getView();
}
