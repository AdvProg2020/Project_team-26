package view.buyeyAccountView.cartView;

import view.singleProductView.SingleProductView;
import view.View;

public enum CartViewValidCommand {
    ShowAllProducts {
        @Override
        public View getView() {
            return new ShowAllproductsInCart();
        }

        @Override
        public String toString() {
            return "show\\s+products";
        }
    },

    ShowProductWithId {
        @Override
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },


    IncreaseNumberOfProductForBuyerWithId {
        @Override
        public View getView() {
            return new IncreaseNumberOfProductForBuyerWithId();
        }

        @Override
        public String toString() {
            return "increase\\s+(.*)";
        }
    },
    DecreaseNumberOfProductForBuyerWithId {
        @Override
        public View getView() {
            return new DecreaseNumberOfProductForBuyerWithId();
        }

        @Override
        public String toString() {
            return "decrease\\s+(.*)";
        }
    },
    ShowTotalPriceToBuyer {
        @Override
        public View getView() {
            return new ShowTotalPriceToBuyer();
        }

        @Override
        public String toString() {
            return "show\\s+total\\s+price";
        }
    },
    Purchase {
        @Override
        public View getView() {
            return new Purchase();
        }

        @Override
        public String toString() {
            return "purchase";
        }
    },
    ;

    public abstract View getView();

}
