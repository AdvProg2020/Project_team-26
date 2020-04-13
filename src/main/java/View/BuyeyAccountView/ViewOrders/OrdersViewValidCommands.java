package View.BuyeyAccountView.ViewOrders;

public enum OrdersViewValidCommands {
    ShowOrdersWithIdToBuyer {
        @Override
        public String toString() {
            return "show\\s+order\\s+(.*)";
        }
    },
    RateTheProductWithItsId {
        @Override
        public String toString() {
            return "rate\\s+(.*) ([1-5]{1})";
        }
    },
}
