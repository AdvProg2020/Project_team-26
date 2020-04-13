package view.buyer.orders;

import view.View;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer {
        @Override
        public View getView() {
            return new ShowOrdersWithIdToBuyer();
        }

        @Override
        public String toString() {
            return "show\\s+order\\s+(.*)";
        }
    },
    RateTheProductWithItsId {
        @Override
        public View getView() {
            return new RateTheProductWithItsId();
        }

        @Override
        public String toString() {
            return "rate\\s+(.*) ([1-5]{1})";
        }
    },
    ;

    public abstract View getView();
}
