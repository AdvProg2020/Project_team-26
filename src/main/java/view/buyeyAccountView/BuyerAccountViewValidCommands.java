package view.buyeyAccountView;

import view.buyeyAccountView.cartView.CartView;
import view.buyeyAccountView.viewOrders.OrdersView;
import view.View;

public enum BuyerAccountViewValidCommands {
    ViewPersonalInfo {
        @Override
        public View getView() {
            return new ViewPersonalInfo();
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

            return new ViewPersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+balance";
        }
    },
    ViewDiscountCodesToBuyer {
        @Override
        public View getView() {
            return new ViewDiscountCodesToBuyer();
        }

        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    };

    public abstract View getView();
}
