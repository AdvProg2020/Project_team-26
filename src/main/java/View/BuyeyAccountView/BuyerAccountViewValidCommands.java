package View.BuyeyAccountView;

import View.BuyeyAccountView.CartView.CartView;
import View.BuyeyAccountView.ViewOrders.OrdersView;
import View.View;

public enum BuyerAccountViewValidCommands {
    ViewPersonalInfo {
        public View getView() {
            return new ViewPersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        public View getView() {
            return new EditTheField();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ViewCart {
        public View getView() {
            return new CartView();
        }

        @Override
        public String toString() {
            return "view\\s+cart";
        }
    },

    ViewOrdersForBuyer {
        public View getView() {
            return new OrdersView();
        }

        @Override
        public String toString() {
            return "view\\s+orders";
        }
    },
    ViewBalanceToBuyer {
        public View getView() {
            return new ViewPersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+balance";
        }
    },
    ViewDiscountCodesToBuyer {
        public View getView() {
            return new ViewDiscountCodesToBuyer();
        }

        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    }
}
