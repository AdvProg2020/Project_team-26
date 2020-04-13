package View.BuyeyAccountView.CartView;

import View.SingleProductView.SingleProductView;
import View.View;

public enum CartViewValidCommand {
    ShowAllProducts {
        public View getView() {
            return new ShowAllproductsInCart();
        }

        @Override
        public String toString() {
            return "show\\s+products";
        }
    },

    ShowProductWithId {
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },


    IncreaseNumberOfProductForBuyerWithId {
        public View getView() {
            return new IncreaseNumberOfProductForBuyerWithId();
        }

        @Override
        public String toString() {
            return "increase\\s+(.*)";
        }
    },
    DecreaseNumberOfProductForBuyerWithId {
        public View getView() {
            return new DecreaseNumberOfProductForBuyerWithId();
        }

        @Override
        public String toString() {
            return "decrease\\s+(.*)";
        }
    },
    ShowTotalPriceToBuyer {
        public View getView() {
            return new ShowTotalPriceToBuyer();
        }

        @Override
        public String toString() {
            return "show\\s+total\\s+price";
        }
    },
    Purchase {
        public View getView() {
            return new Purchase();
        }

        @Override
        public String toString() {
            return "purchase";
        }
    },

}
