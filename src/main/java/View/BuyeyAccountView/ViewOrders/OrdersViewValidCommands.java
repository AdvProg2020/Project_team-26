package View.BuyeyAccountView.ViewOrders;

import View.BuyeyAccountView.CartView.ShowTotalPriceToBuyer;
import View.View;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer {
        public View getView() {
            return new ShowOrdersWithIdToBuyer();
        }

        @Override
        public String toString() {
            return "show\\s+order\\s+(.*)";
        }
    },
    RateTheProductWithItsId {
        public View getView() {
            return new RateTheProductWithItsId();
        }

        @Override
        public String toString() {
            return "rate\\s+(.*) ([1-5]{1})";
        }
    },
}
