package View.BuyeyAccountView.CartView;

public enum CartViewValidCommand {
    ShowAllProducts {
        @Override
        public String toString() {
            return "show\\s+products";
        }
    },

    ShowProductWithId {
        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },

    IncreaseNumberOfProductForBuyerWithId {
        @Override
        public String toString() {
            return "increase\\s+(.*)";
        }
    },
    DecreaseNumberOfProductForBuyerWithId {
        @Override
        public String toString() {
            return "decrease\\s+(.*)";
        }
    },
    ShowTotalPriceToBuyer {
        @Override
        public String toString() {
            return "show\\s+total\\s+price";
        }
    },
    Purchase {
        @Override
        public String toString() {
            return "purchase";
        }
    },

}
